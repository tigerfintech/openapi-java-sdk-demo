package com.tigerbrokers.stock.openapi.demo.socket;

import com.tigerbrokers.stock.openapi.client.socket.ApiAuthentication;
import com.tigerbrokers.stock.openapi.client.socket.ApiComposeCallback;
import com.tigerbrokers.stock.openapi.client.socket.WebSocketClient;
import com.tigerbrokers.stock.openapi.client.struct.enums.Subject;
import com.tigerbrokers.stock.openapi.demo.DefaultApiComposeCallback;
import java.util.HashSet;
import java.util.Set;

import static com.tigerbrokers.stock.openapi.demo.DemoConstants.tigerId;
import static com.tigerbrokers.stock.openapi.demo.DemoConstants.webSocketServerUrl;
import static com.tigerbrokers.stock.openapi.demo.DemoConstants.yourPrivateKey;

/**
 * Description:
 * Created by lijiawen on 2018/05/17.
 */
public class WebSocketDemo {

  public static void main(String[] args) {

    ApiAuthentication authentication = ApiAuthentication.build(tigerId, yourPrivateKey);
    ApiComposeCallback callback = new DefaultApiComposeCallback();
    WebSocketClient client = new WebSocketClient(webSocketServerUrl, authentication, callback);

    client.connect();

    client.subscribe(Subject.Asset);
    client.subscribe(Subject.Position);
    client.subscribe(Subject.OrderStatus);

    Set<String> symbols = new HashSet<>();
    symbols.add("AAPL");
    symbols.add("GOOG");
    symbols.add("00700");
    symbols.add("02318");
    client.subscribeQuote(symbols);
  }
}
