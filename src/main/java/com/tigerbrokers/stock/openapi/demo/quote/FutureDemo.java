package com.tigerbrokers.stock.openapi.demo.quote;

import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
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
import java.util.Arrays;
import org.junit.Test;

import static com.tigerbrokers.stock.openapi.demo.DemoConstants.serverUrl;
import static com.tigerbrokers.stock.openapi.demo.DemoConstants.tigerId;
import static com.tigerbrokers.stock.openapi.demo.DemoConstants.yourPrivateKey;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class FutureDemo {

  private static TigerHttpClient client = new TigerHttpClient(serverUrl, tigerId, yourPrivateKey);

  @Test
  public void get_future_exchange() {
    FutureExchangeResponse response = client.execute(FutureExchangeRequest.newRequest(SecType.FUT.name()));
    System.out.println(Arrays.toString(response.getFutureExchangeItems().toArray()));
  }

  @Test
  public void get_future_continuous_contracts() {
    FutureContractResponse cl = client.execute(FutureContinuousContractRequest.newRequest("ES"));
    System.out.println(cl.getFutureContractItem());
  }

  @Test
  public void get_future_contract_by_contract_code() {
    FutureContractResponse response = client.execute(FutureContractByConCodeRequest.newRequest("CL1902"));
    System.out.println(response.getFutureContractItem());
  }

  @Test
  public void get_future_contract_by_exchange_code() {
    FutureBatchContractResponse response = client.execute(FutureContractByExchCodeRequest.newRequest("CME"));
    System.out.println(response.getFutureContractItems());
  }

  @Test
  public void get_future_current_contract() {
    FutureContractResponse response = client.execute(FutureCurrentContractRequest.newRequest("CL"));
    System.out.println(response.getFutureContractItem());
  }

  @Test
  public void get_future_kline() {
    FutureKlineResponse response = client.execute(
        FutureKlineRequest.newRequest("CL1902", FutureKType.min15, 1545634249489L,
            1545807049489L));
    System.out.println(response.getFutureKlineItems());
  }

  @Test
  public void get_future_real_time_quote() {
    FutureRealTimeQuoteResponse response = client.execute(FutureRealTimeQuoteRequest.newRequest("CL1902"));
    System.out.println(response.getFutureRealTimeItem());
  }

  @Test
  public void get_future_tick() {
    FutureTickResponse response = client.execute(FutureTickRequest.newRequest("CL1902", 0, 1000));
    System.out.println(response.getFutureTickItems());
  }

  @Test
  public void get_future_trading_date() {
    FutureTradingDateResponse response =
        client.execute(FutureTradingDateRequest.newRequest("CL1902", System.currentTimeMillis()));
    System.out.println(response.getFutureTradingDateItem());
  }
}
