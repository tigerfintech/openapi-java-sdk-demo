package com.tigerbrokers.stock.openapi.demo.trade;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderStatus;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.util.builder.AccountParamBuilder;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static com.tigerbrokers.stock.openapi.demo.DemoConstants.serverUrl;
import static com.tigerbrokers.stock.openapi.demo.DemoConstants.tigerId;
import static com.tigerbrokers.stock.openapi.demo.DemoConstants.yourPrivateKey;

/**
 * Description:
 * Created by lijiawen on 2018/07/06.
 */
public class AccountDemo {

  private static TigerHttpClient client = new TigerHttpClient(serverUrl, tigerId, yourPrivateKey);

  @Test
  public void queryContract() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.CONTRACT);
    String bizContent = AccountParamBuilder.instance()
        .account("DU575569")
        .conid("42451645")
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    outputResponse(bizContent, response);
  }

  @Test
  public void queryAccount() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.ACCOUNTS);
    String bizContent = AccountParamBuilder.instance()
        .account("DU575569")
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    outputResponse(bizContent, response);
  }

  @Test
  public void queryAsset() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.ASSETS);
    String bizContent = AccountParamBuilder.instance()
        .account("DU575569")
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    outputResponse(bizContent, response);
  }

  @Test
  public void queryPosition() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.POSITIONS);

    String bizContent = AccountParamBuilder.instance()
        .account("DU575569")
        .currency(Currency.USD)
        .market(Market.US)
        .symbol("AAPL")
        .secType(SecType.STK)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    outputResponse(bizContent, response);
  }

  @Test
  public void getOrders() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.ORDERS);
    List<String> states = new ArrayList<>();
    states.add(OrderStatus.Submitted.name());
    states.add(OrderStatus.Filled.name());

    String bizContent = AccountParamBuilder.instance()
        .account("DU575569")
        .startDate("2018-07-21")
        .endDate("2018-11-28")
        .secType(SecType.STK)
        .market(Market.US)
        .states(states)
        .isBrief(false)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    outputResponse(bizContent, response);
  }

  private void outputResponse(String param, TigerHttpResponse response) {
    if (response != null) {
      System.out.println("request success,param:" + param + ",result:" + response);
    } else {
      System.out.println("request failure,param:" + param);
    }
  }
}
