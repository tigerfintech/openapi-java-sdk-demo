package com.tigerbrokers.stock.openapi.demo.socket;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.socket.ApiAuthentication;
import com.tigerbrokers.stock.openapi.client.socket.ApiComposeCallback;
import com.tigerbrokers.stock.openapi.client.socket.WebSocketClient;
import com.tigerbrokers.stock.openapi.client.struct.enums.Subject;
import com.tigerbrokers.stock.openapi.demo.DefaultApiComposeCallback;
import com.tigerbrokers.stock.openapi.demo.TigerOpenClientConfig;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

/**
 * Description:
 * Created by lijiawen on 2018/05/17.
 */
public class WebSocketDemo {

  private WebSocketClient client;

  public WebSocketDemo() {
    ClientConfig clientConfig = TigerOpenClientConfig.getDefaultClientConfig();
    ApiAuthentication authentication = ApiAuthentication.build(clientConfig.tigerId, clientConfig.privateKey);
    ApiComposeCallback callback = new DefaultApiComposeCallback();
    client = WebSocketClient.getInstance().url(clientConfig.socketServerUrl)
        .authentication(authentication).apiComposeCallback(callback);

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
