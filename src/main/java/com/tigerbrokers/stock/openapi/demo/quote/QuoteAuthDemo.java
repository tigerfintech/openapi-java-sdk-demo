package com.tigerbrokers.stock.openapi.demo.quote;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse;
import com.tigerbrokers.stock.openapi.client.util.builder.AccountParamBuilder;
import com.tigerbrokers.stock.openapi.demo.TigerOpenClientConfig;
import org.junit.Test;

/**
 * @author liutongping
 * @version 1.0
 * @description:
 * @date 2021/11/10 下午2:48
 */
public class QuoteAuthDemo {

  private static TigerHttpClient client = new TigerHttpClient(TigerOpenClientConfig.getDefaultClientConfig());

  @Test
  public void grabAuthQuote() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.GRAB_QUOTE_PERMISSION);
    String bizContent = AccountParamBuilder.instance()
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    outputResponse(bizContent, response);
  }

  @Test
  public void getAuthQuote() {
    TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.GET_QUOTE_PERMISSION);
    String bizContent = AccountParamBuilder.instance()
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
