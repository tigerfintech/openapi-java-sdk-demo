package com.tigerbrokers.stock.openapi.demo.trade;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse;

import com.tigerbrokers.stock.openapi.client.util.builder.AccountParamBuilder;

import static com.tigerbrokers.stock.openapi.demo.DemoConstants.*;

/**
 * Description:
 * Created by lijiawen on 2018/07/06.
 */
public class AccountDemo {

  private static TigerHttpClient client = new TigerHttpClient(serverUrl, tigerId, yourPrivateKey, tigerPubKey);

  public static void main(String[] args) {
    AccountDemo accountDemo = new AccountDemo();
    accountDemo.queryAccount();
  }

  public void queryAccount() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.ACCOUNTS);
    String bizContent = AccountParamBuilder.instance()
        .account("DF1003979")
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
