package com.tigerbrokers.stock.openapi.demo.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureContractItem;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.request.contract.ContractRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureCurrentContractRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.TradeOrderRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse;
import com.tigerbrokers.stock.openapi.client.https.response.contract.ContractResponse;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.TradeOrderResponse;
import com.tigerbrokers.stock.openapi.client.struct.TagValue;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Right;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeInForce;
import com.tigerbrokers.stock.openapi.client.struct.param.OrderParameter;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import com.tigerbrokers.stock.openapi.client.util.builder.TradeParamBuilder;
import com.tigerbrokers.stock.openapi.demo.TigerOpenClientConfig;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Description:
 * Created by lijiawen on 2018/05/31.
 */
public class TradeDemo {
  private static TigerHttpClient client = new TigerHttpClient(TigerOpenClientConfig.getDefaultClientConfig());

  public ContractItem getStockContact() {
    ContractItem contract = null;
    ContractRequest contractRequest = ContractRequest.newRequest(
        new ContractModel("AAPL"));
    ContractResponse contractResponse = client.execute(contractRequest);
    contract = contractResponse.getItem();
    return contract;
  }

  // test market order
  @Test
  public void testMarketOrder() {
    ContractItem contract = getStockContact();

    // use default account
    TradeOrderRequest request = TradeOrderRequest.buildMarketOrder(contract, ActionType.BUY, 10);
    TradeOrderResponse response = client.execute(request);
    outputResponse(request.getApiModel(), response);

    // use account parameter
    request = TradeOrderRequest.buildMarketOrder("402901", contract, ActionType.BUY, 10);
    response = client.execute(request);
    outputResponse(request.getApiModel(), response);
  }

  // 测试限价单
  @Test
  public void testLimitOrder() {
    ContractItem contract = getStockContact();

    // use default account
    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(
        contract, ActionType.BUY, 1, 100.0d);
    TradeOrderResponse response = client.execute(request);
    outputResponse(request.getApiModel(), response);

    // use account parameter
    request = TradeOrderRequest.buildLimitOrder(
        "402901", contract, ActionType.BUY, 1, 100.0d);
    response = client.execute(request);
    outputResponse(request.getApiModel(), response);
  }

  // 测试止损单
  @Test
  public void testStopOrder() {
    ContractItem contract = getStockContact();

    // use default account
    TradeOrderRequest request = TradeOrderRequest.buildStopOrder(
        contract, ActionType.BUY, 1, 120.0d);
    TradeOrderResponse response = client.execute(request);
    outputResponse(request.getApiModel(), response);

    // use account parameter
    request = TradeOrderRequest.buildStopOrder(
        "402901", contract, ActionType.BUY, 1, 120.0d);
    response = client.execute(request);
    outputResponse(request.getApiModel(), response);
  }

  // 测试止损限价单
  @Test
  public void testStopLimitOrder() {
    ContractItem contract = getStockContact();

    // use default account
    TradeOrderRequest request = TradeOrderRequest.buildStopLimitOrder(
        contract, ActionType.BUY, 1,
        150d,130.0d);
    TradeOrderResponse response = client.execute(request);
    outputResponse(request.getApiModel(), response);

    // use account parameter
    request = TradeOrderRequest.buildStopLimitOrder(
        "402901", contract, ActionType.BUY, 1,
        150d,130.0d);
    response = client.execute(request);
    outputResponse(request.getApiModel(), response);
  }

  // 测试跟踪止损单
  @Test
  public void testTrailOrder() {
    ContractItem contract = getStockContact();

    // use default account
    TradeOrderRequest request = TradeOrderRequest.buildTrailOrder(
        contract, ActionType.BUY, 1,
        10d,130.0d);
    TradeOrderResponse response = client.execute(request);
    outputResponse(request.getApiModel(), response);

    // use account parameter. omnibus account currently not supported
    request = TradeOrderRequest.buildTrailOrder(
        "402901", contract, ActionType.BUY, 1,
        10d,130.0d);
    response = client.execute(request);
    outputResponse(request.getApiModel(), response);
  }

  // 测试附加止盈单
  @Test
  public void testProfitOrder() {
    ContractItem contract = getStockContact();

    // use default account
    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(
        contract, ActionType.BUY, 1, 199d);
    TradeOrderRequest.addProfitTakerOrder(request, 250D, TimeInForce.DAY, Boolean.FALSE);
    TradeOrderResponse response = client.execute(request);
    outputResponse(request.getApiModel(), response);

    // use account parameter
    request = TradeOrderRequest.buildLimitOrder(
        "402901", contract, ActionType.BUY, 1, 199d);
    TradeOrderRequest.addProfitTakerOrder(request, 250D, TimeInForce.DAY, Boolean.FALSE);
    response = client.execute(request);
    outputResponse(request.getApiModel(), response);
  }

  // 测试附加止损单
  @Test
  public void testStopLossOrder() {
    ContractItem contract = getStockContact();

    // use default account
    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(
        contract, ActionType.BUY, 1, 129d);
    TradeOrderRequest.addStopLossOrder(request, 100D, TimeInForce.DAY);
    TradeOrderResponse response = client.execute(request);
    outputResponse(request.getApiModel(), response);

    // use account parameter
    request = TradeOrderRequest.buildLimitOrder(
        "402901", contract, ActionType.BUY, 1, 129d);
    TradeOrderRequest.addStopLossOrder(request, 100D, TimeInForce.DAY);
    response = client.execute(request);
    outputResponse(request.getApiModel(), response);
  }

  // 测试括号订单
  @Test
  public void testBracketsOrder() {
    ContractItem contract = getStockContact();

    // use default account
    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(
        contract, ActionType.BUY, 1, 199d);
    TradeOrderRequest.addBracketsOrder(request, 250D, TimeInForce.DAY, Boolean.FALSE,
        180D, TimeInForce.GTC);
    TradeOrderResponse response = client.execute(request);
    outputResponse(request.getApiModel(), response);

    // use account parameter
    request = TradeOrderRequest.buildLimitOrder(
        "402901", contract, ActionType.BUY, 1, 199d);
    TradeOrderRequest.addBracketsOrder(request, 250D, TimeInForce.DAY, Boolean.FALSE,
        180D, TimeInForce.GTC);
    response = client.execute(request);
    outputResponse(request.getApiModel(), response);
  }

  public ContractItem getOptionContact() {
    ContractRequest contractRequest = ContractRequest.newRequest(
        new ContractModel("AAPL", SecType.OPT.name(), Currency.USD.name(),
            "20211217", 150D, Right.CALL.name()), "402901");

    ContractResponse contractResponse = client.execute(contractRequest);
    ContractItem contract = contractResponse.getItem();
    return contract;
  }

  // 测试限价单
  @Test
  public void testOptionLimitOrder() {
    ContractItem contract = getOptionContact();

    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(
        contract, ActionType.BUY, 1, 0.1d);
    TradeOrderResponse response = client.execute(request);
    outputResponse(request.getApiModel(), response);
  }


  public ContractItem getFutureContact() {
    ContractRequest contractRequest = ContractRequest.newRequest(
        new ContractModel("CL2201", SecType.FUT.name(), Currency.USD.name()), "402901");
    ContractResponse contractResponse = client.execute(contractRequest);
    ContractItem contract = contractResponse.getItem();
    return contract;
  }

  // 测试限价单
  @Test
  public void testFutureLimitOrder() {
    ContractItem contract = getFutureContact();

    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(
        "402901", contract, ActionType.BUY, 1, 1.0d);
    TradeOrderResponse response = client.execute(request);
    outputResponse(request.getApiModel(), response);
  }

  //@Test
  public int getOrderNo() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.ORDER_NO);
    String bizContent = TradeParamBuilder.instance().account("DU575569").buildJson();
    request.setBizContent(bizContent);
    TigerHttpResponse response = client.execute(request);
    if (response != null && !StringUtils.isEmpty(response.getData())) {
      return JSON.parseObject(response.getData()).getIntValue("orderId");
    }
    throw new RuntimeException("获取订单号失败:" + response.getMessage());
  }

  @Test
  public void placeOptionOrder() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.PLACE_ORDER);

    String bizContent = TradeParamBuilder.instance()
        .account("DU575569")
        .orderId(getOrderNo())
        .symbol("AAPL")
        .secType(SecType.OPT)
        .market(Market.US)
        .currency(Currency.USD)
        .expiry("20180810")
        .strike("182.5")
        .right("CALL")
        .multiplier(100F)
        .action(ActionType.BUY)
        .orderType(OrderType.LMT)
        .limitPrice(61.0)
        .totalQuantity(1)
        .outsideRth(true)
        .timeInForce(TimeInForce.DAY)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    outputResponse(bizContent, response);
  }

  @Test
  public void placeWarOrder() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.PLACE_ORDER);

    String bizContent = TradeParamBuilder.instance()
        .account("DU575569")
        .orderId(getOrderNo())
        .symbol("00823")
        .secType(SecType.WAR)
        .market(Market.HK)
        .currency(Currency.HKD)
        .expiry("20181218")
        .strike("74.880")
        .right("PUT")
        .localSymbol("13768")
        .action(ActionType.BUY)
        .orderType(OrderType.LMT)
        .limitPrice(0.590)
        .totalQuantity(10000)
        .timeInForce(TimeInForce.DAY)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    outputResponse(bizContent, response);
  }

  @Test
  public void placeUSStockOrder() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.PLACE_ORDER);

    String bizContent = TradeParamBuilder.instance()
        .account("DU575569")
        .orderId(getOrderNo())
        .symbol("AAPL")
        .secType(SecType.STK)
        .market(Market.US)
        .currency(Currency.USD)
        .action(ActionType.BUY)
        .orderType(OrderType.LMT)
        .limitPrice(182.0)
        .totalQuantity(100)
        .timeInForce(TimeInForce.DAY)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    outputResponse(bizContent, response);
  }

  @Test
  public void placeHKStockOrder() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.PLACE_ORDER);

    String bizContent = TradeParamBuilder.instance()
        .account("DU575569")
        .orderId(getOrderNo())
        .symbol("00700")
        .secType(SecType.STK)
        .market(Market.HK)
        .currency(Currency.HKD)
        .action(ActionType.BUY)
        .orderType(OrderType.LMT)
        .limitPrice(380.0)
        .totalQuantity(100)
        .timeInForce(TimeInForce.DAY)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    outputResponse(bizContent, response);
  }

  @Test
  public void placeConnectStockOrder() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.PLACE_ORDER);

    String bizContent = TradeParamBuilder.instance()
        .account("DU575569")
        .orderId(getOrderNo())
        .symbol("600016")
        .secType(SecType.STK)
        .market(Market.CN)
        .currency(Currency.CNH)
        .action(ActionType.BUY)
        .orderType(OrderType.LMT)
        .limitPrice(9.0)
        .totalQuantity(10)
        .timeInForce(TimeInForce.DAY)
        .outsideRth(false)
        .buildJson();

    request.setBizContent(bizContent);
    TigerHttpResponse response = client.execute(request);
    outputResponse(bizContent, response);
  }

  @Test
  public void placeFutOrder() {
    FutureContractResponse futureResponse = client.execute(FutureCurrentContractRequest.newRequest("CL"));
    FutureContractItem futureContractItem = futureResponse.getFutureContractItem();
    ContractItem contract = ContractItem.convert(futureContractItem);

    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(
        contract, ActionType.BUY, 1, 0.1d);
    TradeOrderResponse response = client.execute(request);
    outputResponse(request.getApiModel(), response);
  }

  @Test
  public void placeAStockOrder() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.PLACE_ORDER);

    String bizContent = TradeParamBuilder.instance()
        .account("DU575569")
        .orderId(getOrderNo())
        .symbol("600016")
        .secType(SecType.STK)
        .market(Market.CN)
        .currency(Currency.CNH)
        .action(ActionType.BUY)
        .orderType(OrderType.LMT)
        .limitPrice(8.0)
        .totalQuantity(100)
        .timeInForce(TimeInForce.DAY)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    outputResponse(bizContent, response);
  }

  @Test
  public void placeAlgoOrder() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.PLACE_ORDER);

    List<String> accounts = new ArrayList<>();
    accounts.add("DU1003980");
    accounts.add("DU1003981");
    List<Double> shares = new ArrayList<>();
    shares.add(300.0);
    shares.add(200.0);
    List<TagValue> tagValues = new ArrayList<>();
    tagValues.add(new TagValue("participation_rate", "0.5"));
    tagValues.add(new TagValue("riskAversion", "GetDon"));
    tagValues.add(new TagValue("forceCompletion", "0"));

    String bizContent = TradeParamBuilder.instance()
        .account("DF1003979")
        .orderId(getOrderNo())
        .symbol("AAPL")
        .allocAccounts(accounts)
        .allocShares(shares)
        .totalQuantity(500)
        .limitPrice(61.0)
        .orderType(OrderType.LMT)
        .action(ActionType.BUY)
        .secType(SecType.STK)
        .currency(Currency.USD)
        .outsideRth(true)
        .algoStrategy("ArrivalPx")
        .algoParams(tagValues)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    outputResponse(bizContent, response);
  }

  @Test
  public void placeCashOrder() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.PLACE_ORDER);

    String bizContent = TradeParamBuilder.instance()
        .account("DU575569")
        .orderId(getOrderNo())
        .symbol("USD")
        .secType(SecType.CASH)
        .currency(Currency.HKD)
        .action(ActionType.BUY)
        .orderType(OrderType.LMT)
        .limitPrice(9.0)
        .totalQuantity(10)
        .timeInForce(TimeInForce.DAY)
        .outsideRth(false)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    outputResponse(bizContent, response);
  }

  @Test
  public void cancelOrder() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.CANCEL_ORDER);
    String bizContent = TradeParamBuilder.instance()
        .account("DU575569")
        .orderId(1000042974)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    outputResponse(bizContent, response);
  }

  @Test
  public void modifyOrder() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.MODIFY_ORDER);
    String bizContent = TradeParamBuilder.instance()
        .account("DU575569")
        .totalQuantity(500)
        .action(ActionType.BUY)
        .limitPrice(61.2)
        .orderId(1000043432)
        .orderType(OrderType.LMT)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    outputResponse(bizContent, response);
  }

  @Test
  public void batchPlaceOrder() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.BATCH_PLACE_ORDER);
    List<OrderParameter> parameterList = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      OrderParameter orderParameter = TradeParamBuilder.instance()
          .account("DU575569")
          .orderId(i == 0 ? getOrderNo() : 0)
          .symbol("AAPL")
          .secType(SecType.STK)
          .market(Market.US)
          .currency(Currency.USD)
          .action(ActionType.BUY)
          .orderType(OrderType.LMT)
          .limitPrice(182.0)
          .totalQuantity(100)
          .timeInForce(TimeInForce.DAY)
          .build();
      parameterList.add(orderParameter);
    }
    request.setBizContent(JSON.toJSONString(parameterList));

    TigerHttpResponse response = client.execute(request);
    outputResponse(request.getBizContent(), response);
  }

  private void outputResponse(String param, TigerHttpResponse response) {
    if (response != null) {
      System.out.println("request success,param:" + param + ",result:" + response);
    } else {
      System.out.println("request failure,param:" + param);
    }
  }

  private void outputResponse(ApiModel model, TradeOrderResponse response) {
    if (response != null) {
      System.out.println("request " + response.getMessage()
          + ",param:" + JSONObject.toJSONString(model)
          + ",result:" + response.getData());
    } else {
      System.out.println("request failure,param:" + model);
    }
  }
}
