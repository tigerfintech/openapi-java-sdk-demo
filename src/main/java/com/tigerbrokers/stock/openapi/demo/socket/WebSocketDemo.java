package com.tigerbrokers.stock.openapi.demo.socket;

import com.tigerbrokers.stock.openapi.client.socket.ApiAuthentication;
import com.tigerbrokers.stock.openapi.client.socket.ApiComposeCallback;
import com.tigerbrokers.stock.openapi.client.socket.WebSocketClient;
import com.tigerbrokers.stock.openapi.client.struct.enums.Subject;
import com.tigerbrokers.stock.openapi.demo.DefaultApiComposeCallback;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

import static com.tigerbrokers.stock.openapi.demo.DemoConstants.tigerId;
import static com.tigerbrokers.stock.openapi.demo.DemoConstants.webSocketServerUrl;
import static com.tigerbrokers.stock.openapi.demo.DemoConstants.yourPrivateKey;

/**
 * Description:
 * Created by lijiawen on 2018/05/17.
 */
public class WebSocketDemo {

  private WebSocketClient client;

  public WebSocketDemo() {
    ApiAuthentication authentication = ApiAuthentication.build(tigerId, yourPrivateKey);
    ApiComposeCallback callback = new DefaultApiComposeCallback();
    client = new WebSocketClient(webSocketServerUrl, authentication, callback);

    client.connect();
  }

  @Test
  public void subscribeAsset() {
    client.subscribe(Subject.Asset);
  }

  @Test
  public void subscribePosition() {
    client.subscribe(Subject.Position);
  }

  @Test
  public void subscribeOrderStatus() {
    client.subscribe(Subject.OrderStatus);
  }

  @Test
  public void subscribeQuote() {
    Set<String> symbols = new HashSet<>();
    symbols.add("AAPL");
    symbols.add("GOOG");
    symbols.add("00700");
    symbols.add("02318");
    client.subscribeQuote(symbols);
  }

  @Test
  public void subscribeOption() {
    Set<String> symbols = new HashSet<>();
    symbols.add("AMD 20181221 17.0 PUT");
    client.subscribeOption(symbols);
  }
}
