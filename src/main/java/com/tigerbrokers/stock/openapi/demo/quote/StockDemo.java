package com.tigerbrokers.stock.openapi.demo.quote;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.CapitalDistributionItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.CapitalFlowItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.HistoryTimelineItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlineItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.MarketItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.QuoteDelayItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.QuoteStockTradeItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.RealTimeQuoteItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.ShortableStockItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.StockBrokerItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.SymbolNameItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.TimelineItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.TradeTickItem;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteCapitalDistributionRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteCapitalFlowRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteContractRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteDelayRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteHistoryTimelineRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteKlineRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteMarketRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteRealTimeQuoteRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteShortableStockRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteStockBrokerRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteStockTradeRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteSymbolNameRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteSymbolRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteTimelineRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteTradeTickRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteCapitalDistributionResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteCapitalFlowResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteContractResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteDelayResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteHistoryTimelineResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteMarketResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuotePermission;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteRealTimeQuoteResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteShortableStockResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockBrokerResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockTradeResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteSymbolNameResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteSymbolResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTimelineResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeTickResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.CapitalPeriod;
import com.tigerbrokers.stock.openapi.client.struct.enums.KType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.RightOption;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.util.builder.AccountParamBuilder;
import com.tigerbrokers.stock.openapi.client.util.builder.QuoteParamBuilder;
import com.tigerbrokers.stock.openapi.demo.TigerOpenClientConfig;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/20.
 */
public class StockDemo {

  private static TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
      TigerOpenClientConfig.getDefaultClientConfig());

  /**
   * 抢占行情权限接口，返回已抢占的行情权限列表
   */
  public List<QuotePermission> grabQuotePermission() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.GRAB_QUOTE_PERMISSION);
    String bizContent = AccountParamBuilder.instance()
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    return JSONObject.parseArray(response.getData(), QuotePermission.class);
  }

  /**
   * 获取已有行情权限列表
   */
  public List<QuotePermission> getQuotePermissions() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.GET_QUOTE_PERMISSION);
    String bizContent = AccountParamBuilder.instance()
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    return JSONObject.parseArray(response.getData(), QuotePermission.class);
  }

  public List<MarketItem> getMarketState(Market market) {
    QuoteMarketResponse response = client.execute(QuoteMarketRequest.newRequest(market));
    return response.getMarketItems();
  }

  public List<String> getAllSymbols(Market market) {
    QuoteSymbolResponse response = client.execute(QuoteSymbolRequest.newRequest(market));
    return response.getSymbols();
  }

  public List<SymbolNameItem> getAllSymbolNames(Market market) {
    QuoteSymbolNameResponse response = client.execute(QuoteSymbolNameRequest.newRequest(market));
    return response.getSymbolNameItems();
  }

  public List<KlineItem> getKline(List<String> symbols, String beginDate, String endDate, int limit) {
    QuoteKlineResponse response = client.execute(QuoteKlineRequest.newRequest(symbols, KType.day, beginDate, endDate)
        .withLimit(limit)
        .withRight(RightOption.br));
    return response.getKlineItems();
  }

  public List<RealTimeQuoteItem> getRealtimeQuote(List<String> symbols) {
    QuoteRealTimeQuoteResponse response = client.execute(QuoteRealTimeQuoteRequest.newRequest(symbols));
    return response.getRealTimeQuoteItems();
  }

  public List<TradeTickItem> getTradeTick(List<String> symbols) {
    QuoteTradeTickResponse response = client.execute(QuoteTradeTickRequest.newRequest(symbols));
    return response.getTradeTickItems();
  }

  public List<TimelineItem> getTimeline(List<String> symbols, long beginTime) {
    QuoteTimelineResponse response =
        client.execute(QuoteTimelineRequest.newRequest(symbols, beginTime));
    return response.getTimelineItems();
  }

  public List<HistoryTimelineItem> getHistoryTimeline(List<String> symbols, String beginDate) {
    QuoteHistoryTimelineRequest request = QuoteHistoryTimelineRequest.newRequest(symbols, beginDate);
    request.withRight(RightOption.br);
    QuoteHistoryTimelineResponse response = client.execute(request);
    return response.getTimelineItems();
  }

  public List<ContractItem> getQuoteContract(String symbol, String expiry) {
    QuoteContractResponse response = client.execute(QuoteContractRequest.newRequest(symbol, SecType.WAR, expiry));
    return response.getContractItems();
  }

  public List<ShortableStockItem> getQuoteShortableStocks(List<String> symbols) {
    QuoteShortableStockResponse response = client.execute(QuoteShortableStockRequest.newRequest(symbols));
    return response.getShortableStockItems();
  }

  public List<QuoteStockTradeItem> getQuoteStockTrade(List<String> symbols) {
    QuoteStockTradeResponse response = client.execute(QuoteStockTradeRequest.newRequest(symbols));
    return response.getStockTradeItems();
  }

  public List<QuoteDelayItem> getQuoteDelayQuote(List<String> symbols) {
    QuoteDelayResponse response = client.execute(QuoteDelayRequest.newRequest(symbols));
    return response.getQuoteDelayItems();
  }

  public String getStockDetail(List<String> symbols, Market market) {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.STOCK_DETAIL);

    String bizContent = QuoteParamBuilder.instance()
        .symbols(symbols)
        .market(market)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    return response.getData();
  }

  /**
   * only support HK market
   * @param symbol
   * @param limit
   * @return
   */
  public StockBrokerItem getStockBroker(String symbol, Integer limit) {
    QuoteStockBrokerRequest request = QuoteStockBrokerRequest.newRequest(symbol,
        limit, Language.en_US);
    QuoteStockBrokerResponse response = client.execute(request);
    if (response.isSuccess()) {
      return response.getStockBrokerItem();
    } else {
      System.err.println("response error:" + response.getMessage());
      return null;
    }
  }

  public CapitalDistributionItem getCapitalDistribution(Market market, String symbol) {
    QuoteCapitalDistributionRequest request =
        QuoteCapitalDistributionRequest.newRequest(symbol, market);
    QuoteCapitalDistributionResponse response = client.execute(request);
    if (response.isSuccess()) {
      return response.getCapitalDistributionItem();
    } else {
      System.err.println("response error:" + response.getMessage());
      return null;
    }
  }

  public CapitalFlowItem getCapitalFlow(Market market, CapitalPeriod period, String symbol,
      Long beginTime, Long EndTime, Integer limit) {
    QuoteCapitalFlowRequest request = QuoteCapitalFlowRequest.newRequest(
        symbol, market, period);
    request.setBeginTime(beginTime);// include
    request.setEndTime(EndTime);// exclude
    request.setLimit(limit);
    QuoteCapitalFlowResponse response = client.execute(request);
    if (response.isSuccess()) {
      return response.getCapitalFlowItem();
    } else {
      System.err.println("response error:" + response.getMessage());
      return null;
    }
  }

  public static void main(String[] args) {
    StockDemo stockDemo = new StockDemo();

    //行情抢占
    //List<QuotePermission> quotePermissions = stockDemo.grabQuotePermission();
    //System.out.println(JSONObject.toJSONString(quotePermissions));
    //[{"expireAt":-1,"name":"hkStockQuoteLv2"},{"expireAt":-1,"name":"aStockQuoteLv1"}]

    //获取行情抢占结果
    //List<QuotePermission> quotePermissions1 = stockDemo.getQuotePermissions();
    //System.out.println(JSONObject.toJSONString(quotePermissions1));

    //获取美股市场状态
    //List<MarketItem> marketState = stockDemo.getMarketState(Market.US);
    //System.out.println(JSONObject.toJSONString(marketState));
    //[{"market":"US","marketStatus":"Pre-Market Trading","openTime":"09-30 09:30:00 EDT","status":"PRE_HOUR_TRADING"}]

    //获取美股全部标的symbol和名称
    //List<SymbolNameItem> allSymbolNames = stockDemo.getAllSymbolNames(Market.US);
    //System.out.println(allSymbolNames);
    //[SymbolNameItem{symbol='.DJI', name='DJIA'}, SymbolNameItem{symbol='.IXIC', name='NASDAQ'}, SymbolNameItem{symbol='.SPX', name='S&P 500'}, SymbolNameItem{symbol='.XSP', name='S&P 500 Mini-SPX'}, SymbolNameItem{symbol='A', name='Agilent'}]

    //获取美股全部标的symbol
    //List<String> allSymbols = stockDemo.getAllSymbols(Market.US);
    //System.out.println(allSymbols);
    //[ZIONO, ZIONP, ZIP, ZIVO, ZIVOW, ZKIN, ZLAB, ZM, ZNH, ZNTL, ZOES, ZOM, ZROZ, ZS, ZSL, ZT, ZTAQU, ZTEK, ZTO, ZTR, ZTS, ZUMZ, ZUO, ZVIA, ZVO, ZWRK, ZWRKU, ZWRKW, ZWS, ZY, ZYME, ZYNE, ZYXI]

    //List<KlineItem> klineItems = stockDemo.getKline(Lists.newArrayList("AAPL"), "2022-01-01", "2022-01-31", 5);
    //System.out.println(JSONObject.toJSONString(klineItems));
    //[{"items":[{"close":182.01,"high":182.88,"low":177.71,"open":177.83,"time":1641186000000,"volume":104701220},{"close":179.7,"high":182.94,"low":179.12,"open":182.63,"time":1641272400000,"volume":99310438},{"close":174.92,"high":180.17,"low":174.64,"open":179.61,"time":1641358800000,"volume":94537602},{"close":172.0,"high":175.3,"low":171.64,"open":172.7,"time":1641445200000,"volume":96903955},{"close":172.17,"high":174.14,"low":171.03,"open":172.89,"time":1641531600000,"volume":86709147}],"nextPageToken":"a2xpbmUyLjB8QUFQTHxkYXl8MTY0MDk2NjQwMDAwMHwxNjQxNTMxNjMwMDAw","period":"day","symbol":"AAPL"}]

    //List<RealTimeQuoteItem> quoteItems = stockDemo.getRealtimeQuote(Lists.newArrayList("AAPL"));
    //System.out.println(JSONObject.toJSONString(quoteItems));
    //[{"askPrice":142.25,"askSize":4,"bidPrice":142.24,"bidSize":200,"close":142.48,"high":146.72,"latestPrice":142.48,"latestTime":1664481600000,"low":140.68,"open":146.1,"preClose":149.84,"status":"NORMAL","symbol":"AAPL","volume":128138237}]

    //List<TradeTickItem> tickItems = stockDemo.getTradeTick(Lists.newArrayList("AAPL"));
    //System.out.println(JSONObject.toJSONString(tickItems));
    //[{"beginIndex":528282,"endIndex":528482,"items":[{"price":142.57,"time":1664481599606,"type":"-","volume":747},{"price":142.59,"time":1664481599654,"type":"*","volume":70},{"price":142.57,"time":1664481599659,"type":"*","volume":39},{"price":142.57,"time":1664481599659,"type":"-","volume":2600},{"price":142.57,"time":1664481599659,"type":"-","volume":2505},{"price":142.6,"time":1664481599722,"type":"+","volume":2585},{"price":142.6,"time":1664481599743,"type":"*","volume":6},{"price":142.57,"time":1664481599759,"type":"*","volume":28},{"price":142.57,"time":1664481599770,"type":"-","volume":167},{"price":142.57,"time":1664481599770,"type":"*","volume":60},{"price":142.53,"time":1664481599785,"type":"*","volume":1},{"price":142.57,"time":1664481599806,"type":"*","volume":20},{"price":142.6,"time":1664481599822,"type":"*","volume":2},{"price":142.59,"time":1664481599874,"type":"+","volume":300}]

    //StockBrokerItem stockBrokerItem = stockDemo.getStockBroker("00700", 10);
    //System.out.println(JSONObject.toJSONString(stockBrokerItem));
    // {"bidBroker":[{"level":1,"price":287.8,"brokerCount":7,"broker":[{"name":"China Investment","id":"6997"},{"name":"China Investment","id":"6998"},{"name":"BOCI","id":"8134"},{"name":"China Investment","id":"6998"},{"name":"China Innovation","id":"5999"},{"name":"China Investment","id":"6996"},{"name":"J.P. Morgan","id":"5342"}]},{"level":2,"price":287.6,"brokerCount":3,"broker":[{"name":"China Investment","id":"6998"},{"name":"China Investment","id":"6999"},{"name":"China Innovation","id":"5998"}]}],"symbol":"00700","askBroker":[{"level":1,"price":288.0,"brokerCount":10,"broker":[{"name":"FUTU Securities","id":"8461"},{"name":"China Innovation","id":"5998"},{"name":"ICBC (Asia)","id":"8117"},{"name":"China Innovation","id":"5998"},{"name":"China Investment","id":"6999"},{"name":"HSBC Securities Brokers","id":"8577"},{"name":"China Investment","id":"6996"},{"name":"China Investment","id":"6999"},{"name":"FUTU Securities","id":"8462"},{"name":"HSBC Securities Brokers","id":"8575"}]}]}

    //CapitalDistributionItem capitalDistributionItem = stockDemo.getCapitalDistribution(Market.HK, "00700");
    //System.out.println(JSONObject.toJSONString(capitalDistributionItem));
    // {"inBig":1.00444474E9,"netInflow":7.4255118E8,"symbol":"00700","outBig":6.4128384E8,"outAll":4.28817328E9,"inAll":5.03072446E9,"inMid":7.0993346E8,"inSmall":3.31634626E9,"outMid":6.0280492E8,"outSmall":3.04408452E9}

    //CapitalFlowItem capitalFlowItem = stockDemo.getCapitalFlow(
    //    Market.US, CapitalPeriod.week, "AAPL",
    //    1667491200000L, 1668528000000L, 10);
    //System.out.println(JSONObject.toJSONString(capitalFlowItem));
    // {"symbol":"AAPL","period":"week","items":[{"netInflow":-4.3444489809E8,"time":"2022-10-31","timestamp":1667188800000},{"netInflow":-6.0454399081E8,"time":"2022-11-07","timestamp":1667797200000},{"netInflow":-4.7228242309E8,"time":"2022-11-14","timestamp":1668402000000}]}

    List<TimelineItem> timeline = stockDemo.getTimeline(Lists.newArrayList("AAPL"), System.currentTimeMillis());
    System.out.println(JSONObject.toJSONString(timeline));
  }
}
