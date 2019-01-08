package com.tigerbrokers.stock.openapi.demo.quote;

import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionCommonModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionKlineModel;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionBriefQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionChainQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionExpirationQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionKlineQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionTradeTickQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionBriefResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionChainResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionExpirationResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionKlineResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionTradeTickResponse;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static com.tigerbrokers.stock.openapi.demo.DemoConstants.serverUrl;
import static com.tigerbrokers.stock.openapi.demo.DemoConstants.tigerId;
import static com.tigerbrokers.stock.openapi.demo.DemoConstants.yourPrivateKey;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class OptionDemo {

  private static TigerHttpClient client = new TigerHttpClient(serverUrl, tigerId, yourPrivateKey);

  @Test
  public void option_expiration() {
    ApiLogger.setDebugEnabled(true);
    List<String> symbols = new ArrayList<>();
    symbols.add("AAPL");
    OptionExpirationResponse response = client.execute(new OptionExpirationQueryRequest(symbols));
    if (response.isSuccess()) {
      System.out.println(Arrays.toString(response.getOptionExpirationItems().toArray()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }
  }

  @Test
  public void option_brief() {
    OptionCommonModel model = new OptionCommonModel();
    model.setSymbol("AAPL");
    model.setRight("CALL");
    model.setStrike("95.0");
    model.setExpiry("2019-01-11");
    OptionBriefResponse response = client.execute(OptionBriefQueryRequest.of(model));
    if (response.isSuccess()) {
      System.out.println(Arrays.toString(response.getOptionBriefItems().toArray()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }
  }

  @Test
  public void option_chain() {
    OptionChainModel model = new OptionChainModel();
    model.setSymbol("AAPL");
    model.setExpiry("2019-01-11");

    OptionChainResponse response = client.execute(OptionChainQueryRequest.of(model));
    if (response.isSuccess()) {
      System.out.println(Arrays.toString(response.getOptionChainItems().toArray()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }
  }

  @Test
  public void option_kline() {
    OptionKlineModel model = new OptionKlineModel();
    model.setSymbol("BABA");
    model.setRight("CALL");
    model.setStrike("129.0");
    model.setExpiry("2019-01-04");
    model.setBeginTime("2018-12-10");
    model.setEndTime("2019-12-26");

    OptionKlineResponse response = client.execute(OptionKlineQueryRequest.of(model));
    if (response.isSuccess()) {
      System.out.println(Arrays.toString(response.getKlineItems().toArray()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }
  }

  @Test
  public void option_trade_tick() {
    OptionCommonModel model = new OptionCommonModel();
    model.setSymbol("AAPL");
    model.setRight("CALL");
    model.setStrike("95.0");
    model.setExpiry("2019-01-11");

    OptionTradeTickResponse response = client.execute(OptionTradeTickQueryRequest.of(model));
    if (response.isSuccess()) {
      System.out.println(Arrays.toString(response.getOptionTradeTickItems().toArray()));
    } else {
      System.out.println("response error:" + response.getMessage());
    }
  }
}
