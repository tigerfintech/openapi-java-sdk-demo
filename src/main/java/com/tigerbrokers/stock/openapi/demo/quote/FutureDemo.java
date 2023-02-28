package com.tigerbrokers.stock.openapi.demo.quote;

import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureExchangeItem;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureKlineBatchItem;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureRealTimeItem;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureTickBatchItem;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureTradingDateItem;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureContinuousContractRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureContractByConCodeRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureContractByExchCodeRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureCurrentContractRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureExchangeRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureKlineRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureRealTimeQuoteRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureTickRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureTradingDateRequest;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureBatchContractResponse;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractResponse;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureExchangeResponse;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureKlineResponse;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureRealTimeQuoteResponse;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureTickResponse;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureTradingDateResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.FutureKType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.demo.TigerOpenClientConfig;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class FutureDemo {

  private static TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
      TigerOpenClientConfig.getDefaultClientConfig());

  public List<FutureExchangeItem>  getFutureExchange(SecType secType) {
    FutureExchangeResponse response = client.execute(FutureExchangeRequest.newRequest(secType.name()));
    return response.getFutureExchangeItems();
  }

  public FutureContractItem getFutureContinuousContracts(String type) {
    FutureContractResponse cl = client.execute(FutureContinuousContractRequest.newRequest(type));
    return cl.getFutureContractItem();
  }

  public FutureContractItem getFutureContractByContractCode(String contractCode) {
    FutureContractResponse response = client.execute(FutureContractByConCodeRequest.newRequest(contractCode));
    return response.getFutureContractItem();
  }

  public List<FutureContractItem>  getFutureContractByExchangeCode(String exchange) {
    FutureBatchContractResponse response = client.execute(FutureContractByExchCodeRequest.newRequest(exchange));
    return response.getFutureContractItems();
  }

  public FutureContractItem getFutureCurrentContract(String type) {
    FutureContractResponse response = client.execute(FutureCurrentContractRequest.newRequest(type));
    return response.getFutureContractItem();
  }

  public List<FutureKlineBatchItem>  getFutureKline(List<String> contractCodes, Date beginTime, Date endTime, int limit) {
    FutureKlineResponse response = client.execute(
        FutureKlineRequest.newRequest(contractCodes, FutureKType.min15, beginTime.getTime(),
            endTime.getTime(), limit));
    return response.getFutureKlineItems();
  }

  public List<FutureRealTimeItem> getFutureRealtimeQuote(List<String> contractCodes) {
    FutureRealTimeQuoteResponse response = client.execute(FutureRealTimeQuoteRequest.newRequest(contractCodes));
    return response.getFutureRealTimeItems();
  }

  public FutureTickBatchItem getFutureTick(String contractCode, int beginIndex, int endIndex) {
    FutureTickResponse response = client.execute(FutureTickRequest.newRequest(contractCode, beginIndex, endIndex));
    return response.getFutureTickItems();
  }

  public FutureTradingDateItem getFutureTradingDate(String contractCode) {
    FutureTradingDateResponse response = client.execute(FutureTradingDateRequest.newRequest(contractCode));
    return response.getFutureTradingDateItem();
  }
}
