package com.tigerbrokers.stock.openapi.demo.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.TradeOrderItem;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.request.contract.ContractRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureCurrentContractRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.TradeOrderRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse;
import com.tigerbrokers.stock.openapi.client.https.response.contract.ContractResponse;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.TradeOrderResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Right;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeInForce;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import com.tigerbrokers.stock.openapi.client.util.builder.AccountParamBuilder;
import com.tigerbrokers.stock.openapi.client.util.builder.TradeParamBuilder;
import com.tigerbrokers.stock.openapi.demo.TigerOpenClientConfig;

/**
 * Description:
 * Created by lijiawen on 2018/05/31.
 */
public class TradeDemo {

  private static TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
      TigerOpenClientConfig.getDefaultClientConfig());

  public TradeOrderItem placeMarketOrder(ContractItem contract, ActionType actionType, int quantity) {
    TradeOrderRequest request = TradeOrderRequest.buildMarketOrder(contract, actionType, quantity);
    TradeOrderResponse response = client.execute(request);
    return response.getItem();
  }

  public TradeOrderItem placeLimitOrder(ContractItem contract, ActionType actionType, int quantity, double limitPrice) {
    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(contract, actionType, quantity, limitPrice);
    TradeOrderResponse response = client.execute(request);
    return response.getItem();
  }

  public TradeOrderItem placeStopOrder(ContractItem contract, ActionType actionType, int quantity, double auxPrice) {
    TradeOrderRequest request = TradeOrderRequest.buildStopOrder(contract, actionType, quantity, auxPrice);
    TradeOrderResponse response = client.execute(request);
    return response.getItem();
  }

  public TradeOrderItem placeStopLimitOrder(ContractItem contract, ActionType actionType, int quantity,
      double limitPrice, double auxPrice) {
    TradeOrderRequest request =
        TradeOrderRequest.buildStopLimitOrder(contract, actionType, quantity, limitPrice, auxPrice);
    TradeOrderResponse response = client.execute(request);
    return response.getItem();
  }

  public TradeOrderItem placeTrailOrder(ContractItem contract, ActionType actionType, int quantity,
      double trailingPercent, double auxPrice) {
    TradeOrderRequest request =
        TradeOrderRequest.buildTrailOrder(contract, actionType, quantity, trailingPercent, auxPrice);
    TradeOrderResponse response = client.execute(request);
    return response.getItem();
  }

  public TradeOrderItem placeProfitOrder(ContractItem contract, ActionType actionType, int quantity, double limitPrice,
      double profitTakerPrice) {
    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(contract, actionType, quantity, limitPrice);
    TradeOrderRequest.addProfitTakerOrder(request, profitTakerPrice, TimeInForce.DAY, Boolean.FALSE);
    TradeOrderResponse response = client.execute(request);
    return response.getItem();
  }

  public TradeOrderItem placeStopLossOrder(ContractItem contract, ActionType actionType, int quantity,
      double limitPrice,
      double stopLossPrice, TimeInForce timeInForce) {
    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(contract, actionType, quantity, limitPrice);
    TradeOrderRequest.addStopLossOrder(request, stopLossPrice, timeInForce);
    TradeOrderResponse response = client.execute(request);
    return response.getItem();
  }

  public TradeOrderItem placeBracketsOrder(ContractItem contract, ActionType actionType, int quantity,
      double limitPrice,
      double profitTakerPrice, double stopLossPrice, TimeInForce timeInForce) {
    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(contract, actionType, quantity, limitPrice);
    TradeOrderRequest.addBracketsOrder(request, profitTakerPrice, timeInForce, Boolean.FALSE, stopLossPrice,
        timeInForce);
    TradeOrderResponse response = client.execute(request);
    return response.getItem();
  }

  public TradeOrderItem placeOptionLimitOrder(ContractItem contract, ActionType actionType, int quantity,
      double limitPrice) {
    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(contract, actionType, quantity, limitPrice);
    TradeOrderResponse response = client.execute(request);
    return response.getItem();
  }

  public TradeOrderItem placeFutureLimitOrder(String type, ActionType actionType, int quantity, double limitPrice) {
    FutureContractResponse futureResponse = client.execute(FutureCurrentContractRequest.newRequest(type));
    FutureContractItem futureContractItem = futureResponse.getFutureContractItem();
    ContractItem contract = ContractItem.convert(futureContractItem);

    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(contract, actionType, quantity, limitPrice);
    TradeOrderResponse response = client.execute(request);
    return response.getItem();
  }

  public TradeOrderItem placeWarrantContract(String symbol, double strike, Right right, String expiry,
      ActionType actionType,
      int quantity, double limitPrice) {
    ContractModel contractModel = new ContractModel(symbol, SecType.WAR.name());
    contractModel.setStrike(strike);
    contractModel.setRight(right.name());
    contractModel.setExpiry(expiry);
    ContractRequest contractRequest = ContractRequest.newRequest(contractModel);
    ContractResponse contractResponse = client.execute(contractRequest);
    ContractItem contract = contractResponse.getItem();

    //第二种方式，手动创建合约
    //contract = ContractItem.buildWarrantContract(symbol, expiry, strike, right.name());

    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(contract, actionType, quantity, limitPrice);
    TradeOrderResponse response = client.execute(request);
    return response.getItem();
  }

  public TradeOrderItem placeCbbcContract(String symbol, double strike, Right right, String expiry,
      ActionType actionType,
      int quantity, double limitPrice) {
    ContractModel contractModel = new ContractModel(symbol, SecType.IOPT.name());
    contractModel.setStrike(strike);
    contractModel.setRight(right.name());
    contractModel.setExpiry(expiry);

    ContractRequest contractRequest = ContractRequest.newRequest(contractModel);
    ContractResponse contractResponse = client.execute(contractRequest);
    ContractItem contract = contractResponse.getItem();

    //第二种方式，手动创建合约
    //contract = ContractItem.buildCbbcContract(symbol, expiry, strike, right.name());

    TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(contract, actionType, quantity, limitPrice);
    TradeOrderResponse response = client.execute(request);
    return response.getItem();
  }

  public int getOrderNo(String account) {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.ORDER_NO);
    String bizContent = TradeParamBuilder.instance().account(account).buildJson();
    request.setBizContent(bizContent);
    TigerHttpResponse response = client.execute(request);
    if (response != null && !StringUtils.isEmpty(response.getData())) {
      return JSON.parseObject(response.getData()).getIntValue("orderId");
    }
    throw new RuntimeException("获取订单号失败:" + response.getMessage());
  }

  public JSONObject getOrder(String account, long id) {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.ORDERS);

    String bizContent = AccountParamBuilder.instance()
        .account(account)
        .id(id)
        .buildJson();

    request.setBizContent(bizContent);
    TigerHttpResponse response = client.execute(request);
    return JSON.parseObject(response.getData());
  }

  public long cancelOrder(String account, int orderId) {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.CANCEL_ORDER);
    String bizContent = TradeParamBuilder.instance()
        .account(account)
        .orderId(orderId)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    JSONObject data = JSON.parseObject(response.getData());
    return data.getLong("id");
  }

  public long modifyOrder(String account, int totalQuantity, double limitPrice, int orderId, ActionType actionType,
      OrderType orderType) {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.MODIFY_ORDER);
    String bizContent = TradeParamBuilder.instance()
        .account(account)
        .totalQuantity(totalQuantity)
        .action(actionType)
        .limitPrice(limitPrice)
        .orderId(orderId)
        .orderType(orderType)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    JSONObject data = JSON.parseObject(response.getData());
    return data.getLong("id");
  }
}
