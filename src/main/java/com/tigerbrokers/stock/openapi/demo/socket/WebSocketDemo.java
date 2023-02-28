package com.tigerbrokers.stock.openapi.demo.socket;

import com.google.common.collect.Sets;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.socket.ApiComposeCallback;
import com.tigerbrokers.stock.openapi.client.socket.WebSocketClient;
import com.tigerbrokers.stock.openapi.client.struct.enums.Subject;
import com.tigerbrokers.stock.openapi.demo.DefaultApiComposeCallback;
import com.tigerbrokers.stock.openapi.demo.TigerOpenClientConfig;

import java.util.Set;

/**
 * Description:
 * Created by lijiawen on 2018/05/17.
 */
public class WebSocketDemo {

  private WebSocketClient client;

  public WebSocketDemo() {
    ClientConfig clientConfig = TigerOpenClientConfig.getDefaultClientConfig();
    ApiComposeCallback callback = new DefaultApiComposeCallback();
    client = WebSocketClient.getInstance().clientConfig(clientConfig).apiComposeCallback(callback);
    client.connect();
  }

  public void subscribeAsset() {
    client.subscribe(Subject.Asset);
  }

  public void subscribePosition() {
    client.subscribe(Subject.Position);
  }

  public void subscribeOrder() {
    client.subscribe(Subject.OrderStatus);
  }

  public void subscribeQuote(Set<String> symbols) {
    client.subscribeQuote(symbols);
  }

  public void subscribeOption(Set<String> symbols) {
    client.subscribeOption(symbols);
  }

  public static void main(String[] args) {
    WebSocketDemo webSocketDemo = new WebSocketDemo();
    webSocketDemo.subscribeAsset();
    webSocketDemo.subscribeOrder();
    webSocketDemo.subscribePosition();
    webSocketDemo.subscribeQuote(Sets.newHashSet("AAPL"));
    //webSocketDemo.subscribeOption(Sets.newHashSet("AMD 20181221 17.0 PUT"));
  }
}
