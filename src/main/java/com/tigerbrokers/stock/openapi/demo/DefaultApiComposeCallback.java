package com.tigerbrokers.stock.openapi.demo;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.socket.ApiComposeCallback;
import com.tigerbrokers.stock.openapi.client.socket.WebSocketClient;
import com.tigerbrokers.stock.openapi.client.struct.SubscribedSymbol;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.param.OrderParameter;
import com.tigerbrokers.stock.openapi.client.util.builder.TradeParamBuilder;

/**
 * Description:
 * Created by lijiawen on 2018/05/23.
 */
public class DefaultApiComposeCallback implements ApiComposeCallback {

  private WebSocketClient client;

  @Override
  public void orderNoEnd(JSONObject jsonObject) {
    System.out.println("orderNoEnd:" + jsonObject.toJSONString());
    if (client == null) {
      System.out.println("client is null");
      return;
    }
    Integer orderId = jsonObject.getInteger("orderId");
    if (orderId == null) {
      System.out.println("orderId is null");
      return;
    }
    client.placeOrder(newStockOrder(orderId));
  }

  private static OrderParameter newStockOrder(int orderId) {
    return TradeParamBuilder.instance()
        .orderId(orderId)
        .account("DU575569")
        .symbol("AAPL")
        .totalQuantity(500)
        .limitPrice(51.0)
        .orderType(OrderType.LMT)
        .action(ActionType.BUY)
        .secType(SecType.STK)
        .currency(Currency.USD)
        .outsideRth(false)
        .build();
  }

  @Override
  public void previewOrderEnd(JSONObject jsonObject) {
    System.out.println("previewOrderEnd:" + jsonObject.toJSONString());
  }

  @Override
  public void placeOrderEnd(JSONObject jsonObject) {
    System.out.println("placeOrderEnd:" + jsonObject.toJSONString());
  }

  @Override
  public void cancelOrderEnd(JSONObject jsonObject) {
    System.out.println("cancelOrderEnd:" + jsonObject.toJSONString());
  }

  @Override
  public void modifyOrderEnd(JSONObject jsonObject) {
    System.out.println("modifyOrderEnd:" + jsonObject.toJSONString());
  }

  @Override
  public void getAssetEnd(JSONObject jsonObject) {
    System.out.println("getAssetEnd:" + jsonObject.toJSONString());
  }

  @Override
  public void getPositionEnd(JSONObject jsonObject) {
    System.out.println("getPositionEnd:" + jsonObject.toJSONString());
  }

  @Override
  public void getAccountEnd(JSONObject jsonObject) {
    System.out.println("gentAccountEnd:" + jsonObject.toJSONString());
  }

  @Override
  public void orderStatusChange(JSONObject jsonObject) {
    StringBuilder builder = new StringBuilder();
    for (String key : jsonObject.keySet()) {
      builder.append(key).append("=").append(jsonObject.get(key)).append("|");
    }
    System.out.println("order change:" + builder);
  }

  @Override
  public void positionChange(JSONObject jsonObject) {
    StringBuilder builder = new StringBuilder();
    for (String key : jsonObject.keySet()) {
      builder.append(key).append("=").append(jsonObject.get(key)).append("|");
    }
    System.out.println("position change:" + builder);
  }

  @Override
  public void assetChange(JSONObject jsonObject) {
    StringBuilder builder = new StringBuilder();
    for (String key : jsonObject.keySet()) {
      builder.append(key).append("=").append(jsonObject.get(key)).append("|");
    }
    System.out.println("asset change:" + builder);
  }

  @Override
  public void getMarketStateEnd(JSONObject jsonObject) {
    System.out.println("getMarketStateEnd:" + jsonObject.toJSONString());
  }

  @Override
  public void getAllSymbolsEnd(JSONObject jsonObject) {
    System.out.println("getAllSymbolsEnd:" + jsonObject.toJSONString());
  }

  @Override
  public void getAllSymbolNamesEnd(JSONObject jsonObject) {
    System.out.println("getAllSymbolNamesEnd:" + jsonObject.toJSONString());
  }

  @Override
  public void getBriefInfoEnd(JSONObject jsonObject) {
    System.out.println("getBriefInfoEnd:" + jsonObject.toJSONString());
  }

  @Override
  public void getStockDetailEnd(JSONObject jsonObject) {
    System.out.println("getStockDetailEnd:" + jsonObject.toJSONString());
  }

  @Override
  public void getTimelineEnd(JSONObject jsonObject) {
    System.out.println("getTimelineEnd:" + jsonObject.toJSONString());
  }

  @Override
  public void getHourTradingTimelineEnd(JSONObject jsonObject) {
    System.out.println("getHourTradingTimelineEnd:" + jsonObject.toJSONString());
  }

  @Override
  public void getKlineEnd(JSONObject jsonObject) {
    System.out.println("getKlineEnd:" + jsonObject.toJSONString());
  }

  @Override
  public void getTradeTickEnd(JSONObject jsonObject) {
    System.out.println("getTradeTickEnd:" + jsonObject.toJSONString());
  }

  @Override
  public void quoteChange(JSONObject jsonObject) {
    System.out.println("quoteChange:" + jsonObject.toJSONString());
  }

  @Override
  public void subscribeEnd(JSONObject jsonObject) {
    System.out.println("subscribe end:" + jsonObject.toJSONString());
  }

  @Override
  public void cancelSubscribeEnd(JSONObject jsonObject) {
    System.out.println("cancel subscribe end:" + jsonObject.toJSONString());
  }

  @Override
  public void getSubscribedSymbolEnd(SubscribedSymbol subscribedSymbol) {
    System.out.println(JSONObject.toJSONString(subscribedSymbol));
  }

  @Override
  public void client(WebSocketClient client) {
    this.client = client;
  }

  @Override
  public void error(String errorMsg) {
    System.out.println("receive error:" + errorMsg);
  }

  @Override
  public void error(int id, int errorCode, String errorMsg) {
    System.out.println("receive error id:" + id + ",errorCode:" + errorCode + ",errorMsg:" + errorMsg);
  }

  @Override
  public void connectionClosed() {
    System.out.println("connection closed.");
  }

  @Override
  public void connectAck() {
    System.out.println("connect ack.");
  }
}
