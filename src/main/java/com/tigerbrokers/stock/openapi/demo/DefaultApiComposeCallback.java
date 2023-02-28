package com.tigerbrokers.stock.openapi.demo;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.socket.ApiComposeCallback;
import com.tigerbrokers.stock.openapi.client.socket.data.TradeTick;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.AssetData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.OrderStatusData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.OrderTransactionData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.PositionData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBBOData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBasicData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteDepthData;
import com.tigerbrokers.stock.openapi.client.struct.SubscribedSymbol;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.ProtoMessageUtil;

/**
 * Description:
 * Created by lijiawen on 2018/05/23.
 */
public class DefaultApiComposeCallback implements ApiComposeCallback {

  @Override
  public void orderStatusChange(OrderStatusData data) {
    ApiLogger.info("orderStatusChange:" + ProtoMessageUtil.toJson(data));
  }

  @Override
  public void orderTransactionChange(OrderTransactionData data) {
    ApiLogger.info("orderTransactionChange:" + ProtoMessageUtil.toJson(data));
  }

  @Override
  public void positionChange(PositionData data) {
    ApiLogger.info("positionChange:" + ProtoMessageUtil.toJson(data));
  }

  @Override
  public void assetChange(AssetData data) {
    ApiLogger.info("assetChange:" + ProtoMessageUtil.toJson(data));
  }

  @Override
  public void quoteChange(QuoteBasicData data) {
    ApiLogger.info("quoteChange:" + ProtoMessageUtil.toJson(data));
  }
  @Override
  public void quoteAskBidChange(QuoteBBOData data) {
    ApiLogger.info("quoteAskBidChange:" + ProtoMessageUtil.toJson(data));
  }

  @Override
  public void tradeTickChange(TradeTick data) {
    ApiLogger.info("tradeTickChange:" + JSONObject.toJSONString(data));
  }

  @Override
  public void optionChange(QuoteBasicData data) {
    ApiLogger.info("optionChange:" + ProtoMessageUtil.toJson(data));
  }
  @Override
  public void optionAskBidChange(QuoteBBOData data) {
    ApiLogger.info("optionAskBidChange:" + ProtoMessageUtil.toJson(data));
  }

  @Override
  public void futureChange(QuoteBasicData data) {
    ApiLogger.info("futureChange:" + ProtoMessageUtil.toJson(data));
  }
  @Override
  public void futureAskBidChange(QuoteBBOData data) {
    ApiLogger.info("futureAskBidChange:" + ProtoMessageUtil.toJson(data));
  }

  @Override
  public void depthQuoteChange(QuoteDepthData data) {
    ApiLogger.info("depthQuoteChange:" + ProtoMessageUtil.toJson(data));
  }

  @Override
  public void subscribeEnd(int id, String subject, String result) {
    ApiLogger.info("subscribe " + subject + " end. id:" + id + ", " + result);
  }

  @Override
  public void cancelSubscribeEnd(int id, String subject, String result) {
    ApiLogger.info("cancel subscribe " + subject + " end. id:" + id + ", " + result);
  }

  @Override
  public void getSubscribedSymbolEnd(SubscribedSymbol subscribedSymbol) {
    ApiLogger.info(JSONObject.toJSONString(subscribedSymbol));
  }

  @Override
  public void error(String errorMsg) {
    ApiLogger.error("receive error:" + errorMsg);
  }

  @Override
  public void error(int id, int errorCode, String errorMsg) {
    ApiLogger.error("receive error id:" + id + ",errorCode:" + errorCode + ",errorMsg:" + errorMsg);
  }

  @Override
  public void connectionClosed() {
    ApiLogger.info("connection closed.");
  }

  @Override
  public void connectionKickout(int errorCode, String errorMsg) {
    ApiLogger.info(errorMsg + " and the connection is closed.");
  }

  @Override
  public void connectionAck() {
    ApiLogger.info("connect ack.");
  }

  @Override
  public void connectionAck(int i, int i1) {
    ApiLogger.info("connect ack. sendInterval:{}, serverReceiveInterval:{}", i, i1);
  }

  @Override
  public void hearBeat(String s) {

  }

  @Override
  public void serverHeartBeatTimeOut(String s) {

  }
}
