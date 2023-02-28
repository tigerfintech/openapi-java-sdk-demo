package com.tigerbrokers.stock.openapi.demo.example.nasdaq100;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlineItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlinePoint;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.PrimeAssetItem;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteKlineRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteRealTimeQuoteRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.PrimeAssetRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.TradeOrderRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteRealTimeQuoteResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PrimeAssetResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.TradeOrderResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Category;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.KType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.util.builder.AccountParamBuilder;
import com.tigerbrokers.stock.openapi.client.util.builder.TradeParamBuilder;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

/**
 * 动量策略示例。定期运行策略，每次选取股票池中周期内涨幅最高的若干股票，作为本次调仓的目标股票买入持有，对先前持仓中未入选的股票进行平仓。
 */
public class Nasdaq100 {

  public static class TigerOpenClientConfig {

    static {
      ClientConfig clientConfig = ClientConfig.DEFAULT_CONFIG;
      clientConfig.tigerId = "your tiger id";
      clientConfig.defaultAccount = "your account";
      clientConfig.privateKey = "you private key string";
    }

    public static ClientConfig getDefaultClientConfig() {
      return ClientConfig.DEFAULT_CONFIG;
    }
  }

  /*请求历史行情的天数*/
  private static final int HISTORY_DAYS = 100;
  /*请求历史的批量大小*/
  private static final int BATCH_SIZE = 50;
  /*每次请求symbol的个数*/
  private static final int REQUEST_SYMBOLS_SIZE = 50;

  /*计算动量的时间周期*/
  private static final int MOMENTUM_PERIOD = 30;

  /*持股个数*/
  private static final int HOLDING_NUM = 5;

  /*订单检查次数*/
  private static final int ORDERS_CHECK_MAX_TIMES = 20;

  /* 调仓后隔夜剩余流动性目标占比，剩余流动性占比越高，风控状态越安全，如果隔夜剩余流动性占比过低（比如小于5%), 则存在被强平的风险。*/
  private static final double TARGET_OVERNIGHT_LIQUIDATION_RATIO = 0.6;

  private List<String> selectedSymbols = new ArrayList<>();

  private static TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
      com.tigerbrokers.stock.openapi.demo.TigerOpenClientConfig.getDefaultClientConfig());

  private static final Logger logger = Logger.getLogger(Nasdaq100.class.getName());

  // 纳斯达克100指数成分股 Components of Nasdaq-100. Up to 2021/12/20
  private List<String> UNIVERSE_NDX =
      Arrays.asList("AAPL", "ADBE", "ADI", "ADP", "ADSK", "AEP", "ALGN", "AMAT", "AMD", "AMGN", "AMZN", "ANSS", "ASML",
          "ATVI", "AVGO", "BIDU", "BIIB", "BKNG", "CDNS", "CDW", "CERN", "CHKP", "CHTR", "CMCSA", "COST", "CPRT",
          "CRWD", "CSCO", "CSX", "CTAS", "CTSH", "DLTR", "DOCU", "DXCM", "EA", "EBAY", "EXC", "FAST", "FB",
          "FISV", "FOX", "GILD", "GOOG", "HON", "IDXX", "ILMN", "INCY", "INTC", "INTU", "ISRG",
          "JD", "KDP", "KHC", "KLAC", "LRCX", "LULU", "MAR", "MCHP", "MDLZ", "MELI", "MNST", "MRNA", "MRVL",
          "MSFT", "MTCH", "MU", "NFLX", "NTES", "NVDA", "NXPI", "OKTA", "ORLY", "PAYX", "PCAR", "PDD", "PEP",
          "PTON", "PYPL", "QCOM", "REGN", "ROST", "SBUX", "SGEN", "SIRI", "SNPS", "SPLK", "SWKS", "TCOM", "TEAM",
          "TMUS", "TSLA", "TXN", "VRSK", "VRSN", "VRTX", "WBA", "WDAY", "XEL", "XLNX", "ZM");

  /**
   * 运行策略
   */
  public void run() throws InterruptedException {
    // 抢占行情权限
    grabQuotePerm();
    // 选股
    screenStocks();
    // 调仓
    rebalancePortfolio();
  }

  /**
   * 选股。计算周期内池中股票各自的历史涨跌幅，选取涨幅最高几只作为结果
   */
  public void screenStocks() throws InterruptedException {
    Map<String, List<KlinePoint>> history = getHistory(UNIVERSE_NDX, HISTORY_DAYS, BATCH_SIZE);
    Map<String, Double> momentum = new HashMap<>();
    history.forEach((symbol, klinePoints) -> {
      int size = klinePoints.size();
      Double priceChange =
          (klinePoints.get(size - 1).getClose() - klinePoints.get(size - MOMENTUM_PERIOD).getClose()) / klinePoints.get(
              size - MOMENTUM_PERIOD).getClose();
      momentum.put(symbol, priceChange);
    });
    Map<String, Double> sortedMap = momentum.entrySet().stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .limit(HOLDING_NUM)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    selectedSymbols = new ArrayList<>(sortedMap.keySet());
    logger.info("selected symbols:" + selectedSymbols);
  }

  /**
   * 调仓
   */
  public void rebalancePortfolio() throws InterruptedException {
    if (selectedSymbols.isEmpty()) {
      logger.warning("no selected symbols, strategy exit!");
      return;
    }
    // 平仓未入选股票
    closePosition();
    // 开仓入选股票
    openPosition(getAdjustValue());
  }

  /**
   * 获取调仓金额
   */
  private Double getAdjustValue() {
    // 综合/模拟账户资产
    PrimeAssetItem.Segment asset = getPrimeAsset();
    // 调仓后目标隔夜剩余流动性(隔夜剩余流动性 overnight_liquidation = 含贷款价值总权益 equity_with_loan - 隔夜保证金 overnight_margin)
    // 隔夜剩余流动性比例 = 隔夜剩余流动性 overnight_liquidation / 含贷款价值总权益 equity_with_loan
    double targetOvernightLiquidation = asset.getEquityWithLoan() * TARGET_OVERNIGHT_LIQUIDATION_RATIO;
    return asset.getOvernightLiquidation() - targetOvernightLiquidation;
  }

  /**
   * 将持仓中未入选的股票平仓
   */
  private void closePosition() throws InterruptedException {
    Map<String, Integer> positions = getPositions();
    Set<String> needCloseSymbols = positions.keySet();
    // 持仓中没有入选的需要平仓
    for (String selectedSymbol : selectedSymbols) {
      needCloseSymbols.remove(selectedSymbol);
    }
    if (!needCloseSymbols.isEmpty()) {
      Map<String, Double> latestPrice = getQuote(new ArrayList<>(needCloseSymbols));
      List<TradeOrderRequest> orderRequests = new ArrayList<>();
      needCloseSymbols.forEach(symbol -> {
        ContractItem contract = ContractItem.buildStockContract(symbol, Currency.USD.name());
        TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(contract, ActionType.SELL, positions.get(symbol),
            latestPrice.get(symbol));
        orderRequests.add(request);
      });
      executeOrders(orderRequests);
    }
  }

  /**
   * 对入选的股票进行开仓
   * @Param adjustValue 要调仓的金额
   * @throws InterruptedException
   */
  private void openPosition(Double adjustValue) throws InterruptedException {
    double adjustValuePerStock = adjustValue / HOLDING_NUM;
    if (adjustValue <= 0) {
      logger.info("no enough liquidation");
      return;
    }
    Map<String, Double> latestPrice = getQuote(selectedSymbols);
    List<TradeOrderRequest> orders = new ArrayList<>();
    for (String symbol : selectedSymbols) {
      int quantity = (int) (adjustValuePerStock / latestPrice.get(symbol));
      if (quantity == 0) {
        logger.warning("can not place order with zero quantity" + symbol);
        continue;
      }
      ContractItem contract = ContractItem.buildStockContract(symbol, Currency.USD.name());
      TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(contract, ActionType.BUY, quantity,
          latestPrice.get(symbol));
      orders.add(request);
    }
    executeOrders(orders);
  }

  /**
   * 执行订单
   * @param orderRequests 订单列表
   */
  private void executeOrders(List<TradeOrderRequest> orderRequests) throws InterruptedException {
    orderRequests.forEach(order -> {
      TradeOrderResponse response = client.execute(order);
      logger.info("place order: " + response);
    });
    sleep(20000);
    int i = 0;
    while (i <= ORDERS_CHECK_MAX_TIMES) {
      logger.info("check order");
      JSONArray openOrders = getOrders(ApiServiceType.ACTIVE_ORDERS);
      if (openOrders.isEmpty()) {
        logger.info("no open orders.");
        break;
      }
      // 如果达到最大检查次数还未成交，则撤单
      if (i >= ORDERS_CHECK_MAX_TIMES) {
        for (int k = 0; k < openOrders.size(); ++k) {
          JSONObject order = openOrders.getJSONObject(k);
          cancelOrder(order.getLong("id"));
        }
      }
      i++;
      sleep(20000);
    }

    // 已成交订单
    JSONArray filledOrders = getOrders(ApiServiceType.FILLED_ORDERS);
    logger.info("filledOrders:" + filledOrders.toJSONString());
    // 已撤销订单
    JSONArray inactiveOrders = getOrders(ApiServiceType.INACTIVE_ORDERS);
    logger.info("inactiveOrders:" + inactiveOrders);
  }

  /**
   * 撤单
   * @param id 订单id
   */
  private void cancelOrder(Long id) {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.CANCEL_ORDER);
    String bizContent = TradeParamBuilder.instance()
        .account(TigerOpenClientConfig.getDefaultClientConfig().defaultAccount)
        .id(id)
        .buildJson();
    request.setBizContent(bizContent);
    client.execute(request);
  }

  /**
   * 获取不同状态的最近订单列表
   *
   * @param apiServiceType
   *    ACTIVE_ORDERS 未成交
   *    INACTIVE_ORDERS 已撤销
   *    FILLED_ORDERS 已成交
   */
  private JSONArray getOrders(String apiServiceType) {
    logger.info("getOrders by:" + apiServiceType);
    TigerHttpRequest request = new TigerHttpRequest(apiServiceType);
    String bizContent = AccountParamBuilder.instance()
        .account(TigerOpenClientConfig.getDefaultClientConfig().defaultAccount)
        .startDate(Instant.now().minus(1, ChronoUnit.DAYS).toEpochMilli())
        .endDate(Instant.now().toEpochMilli())
        .secType(SecType.STK)
        .market(Market.US)
        .isBrief(false)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    return JSON.parseObject(response.getData()).getJSONArray("items");
  }

  /**
   * 获取综合/模拟账户资产
   */
  private PrimeAssetItem.Segment getPrimeAsset() {
    PrimeAssetRequest assetRequest =
        PrimeAssetRequest.buildPrimeAssetRequest(TigerOpenClientConfig.getDefaultClientConfig().defaultAccount);
    PrimeAssetResponse primeAssetResponse = client.execute(assetRequest);
    //查询股票相关资产信息
    return primeAssetResponse.getSegment(Category.S);
  }

  /**
   * 获取环球账户资产
   */
  private JSONObject getGlobalAsset() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.ASSETS);
    String bizContent = AccountParamBuilder.instance()
        .account(TigerOpenClientConfig.getDefaultClientConfig().defaultAccount)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    JSONArray assets = JSON.parseObject(response.getData()).getJSONArray("items");
    JSONObject asset1 = assets.getJSONObject(0);
    //Double cashBalance = asset1.getDouble("cashBalance");
    //Double sma = asset1.getDouble("SMA");
    //Double netLiquidation = asset1.getDouble("netLiquidation");
    //JSONArray segments = asset1.getJSONArray("segments");
    //JSONObject segment = segments.getJSONObject(0);
    //String category = segment.getString("category"); // "S" 股票， "C" 期货
    return asset1;
  }

  /**
   * 获取持仓symbol及其对应数量
   **/
  private Map<String, Integer> getPositions() {
    Map<String, Integer> result = new HashMap<>();
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.POSITIONS);

    String bizContent = AccountParamBuilder.instance()
        .account(TigerOpenClientConfig.getDefaultClientConfig().defaultAccount)
        .market(Market.US)
        .secType(SecType.STK)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    if (response.getData() == null || response.getData().isEmpty()) {
      return result;
    }
    JSONArray positions = JSON.parseObject(response.getData()).getJSONArray("items");
    if (positions.isEmpty()) {
      return result;
    }
    for (int i = 0; i < positions.size(); ++i) {
      JSONObject pos = positions.getJSONObject(i);
      result.put(pos.getString("symbol"), pos.getInteger("quantity"));
    }
    return result;
  }

  /**
   * 抢占行情权限
   */
  private void grabQuotePerm() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.GRAB_QUOTE_PERMISSION);
    String bizContent = AccountParamBuilder.instance()
        .buildJson();
    request.setBizContent(bizContent);
    TigerHttpResponse response = client.execute(request);
    logger.info("quote permissions: " + response.getData());
  }

  /**
   * 获取实时行情
   */
  private Map<String, Double> getQuote(List<String> symbols) throws InterruptedException {
    logger.info("getQuote.");
    Map<String, Double> quote = new HashMap<>();
    Collection<List<String>> partitions = partition(symbols, REQUEST_SYMBOLS_SIZE);
    for (List<String> part : partitions) {
      QuoteRealTimeQuoteResponse response = client.execute(QuoteRealTimeQuoteRequest.newRequest(part));
      if (response.isSuccess()) {
        response.getRealTimeQuoteItems().forEach(item -> quote.put(item.getSymbol(), item.getLatestPrice()));
      } else {
        logger.warning("QuoteRealTimeQuoteRequest:" + response.getMessage());
      }
      // 防止触发限流
      sleep(1000);
    }
    return quote;
  }

  /**
   * 获取历史行情
   *
   * @param total 行情总天数
   * @param batchSize 每次请求的天数
   */
  private Map<String, List<KlinePoint>> getHistory(List<String> symbols, int total, int batchSize)
      throws InterruptedException {
    logger.info("getHistory.");
    Map<String, List<KlinePoint>> history = new HashMap<>();
    Collection<List<String>> partitions = partition(symbols, REQUEST_SYMBOLS_SIZE);
    for (List<String> part : partitions) {
      int i = 0;
      long endTime = Instant.now().toEpochMilli();
      while (i < total) {
        QuoteKlineRequest request = QuoteKlineRequest.newRequest(part, KType.day, -1L, endTime);
        request.withLimit(batchSize);
        QuoteKlineResponse response = client.execute(request);
        if (response.isSuccess()) {
          for (KlineItem item : response.getKlineItems()) {
            List<KlinePoint> klinePoints = history.getOrDefault(item.getSymbol(), new ArrayList<>());
            klinePoints.addAll(item.getItems());
            endTime = item.getItems().get(0).getTime();
            history.put(item.getSymbol(), klinePoints);
          }
        } else {
          logger.warning("QuoteKlineRequest:" + response.getMessage());
        }
        i += batchSize;
        // 防止触发限流
        sleep(1000);
      }
    }
    Map<String, List<KlinePoint>> sortedHistory = new HashMap<>();
    history.forEach((symbol, klinePoints) -> {
      klinePoints.sort(Comparator.comparingLong(KlinePoint::getTime));
      sortedHistory.put(symbol, klinePoints);
    });
    return sortedHistory;
  }

  /**
   * 列表批量分组
   */
  static <T> Collection<List<T>> partition(List<T> inputList, int size) {
    final AtomicInteger counter = new AtomicInteger(0);
    return inputList.stream()
        .collect(Collectors.groupingBy(s -> counter.getAndIncrement() / size))
        .values();
  }

  public static void main(String[] args) throws InterruptedException {
    Nasdaq100 strategy = new Nasdaq100();
    strategy.run();
  }
}
