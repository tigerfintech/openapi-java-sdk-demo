package com.tigerbrokers.stock.openapi.demo.socket;

import com.tigerbrokers.stock.openapi.client.struct.enums.Subject;
import com.tigerbrokers.stock.openapi.demo.DefaultApiComposeCallback;
import com.tigerbrokers.stock.openapi.client.socket.ApiAuthentication;
import com.tigerbrokers.stock.openapi.client.socket.ApiComposeCallback;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.param.OrderParameter;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.socket.WebSocketClient;

import com.tigerbrokers.stock.openapi.client.struct.param.QuoteParameter;
import com.tigerbrokers.stock.openapi.client.util.builder.QuoteParamBuilder;
import com.tigerbrokers.stock.openapi.client.util.builder.TradeParamBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.tigerbrokers.stock.openapi.demo.DemoConstants.*;

/**
 * Description:
 * Created by lijiawen on 2018/05/17.
 */
public class WebSocketDemo {

  public static void main(String[] args) {

    ApiAuthentication authentication = ApiAuthentication.build(tigerId, yourPrivateKey);
    ApiComposeCallback callback = new DefaultApiComposeCallback();
    WebSocketClient client = new WebSocketClient(webSocketServerUrl, authentication, callback, true);

    client.connect();
    client.getOrderNo("DU575569");
    client.cancelOrder("DU575569", 1000043236);
    client.modifyOrder(newStockOrder(1000043017));
    client.subscribe(Subject.Asset);
    client.subscribe(Subject.Position);
    client.subscribe(Subject.OrderStatus);
    client.getStockDetail(newStockParam());

    Set<String> symbols = new HashSet<>();
    symbols.add("AAPL");
    symbols.add("GOOG");
    symbols.add("00700");
    symbols.add("02318");
    client.subscribeQuote(symbols);
  }

  private static OrderParameter newStockOrder(int orderId) {
    return TradeParamBuilder.instance()
        .orderId(orderId)
        .account("DU575569")
        .symbol("AAPL")
        .totalQuantity(500)
        .limitPrice(51.0)
        .orderType(OrderType.LMT)
        .action(ActionType.BUY)
        .secType(SecType.STK)
        .currency(Currency.USD)
        .outsideRth(false)
        .build();
  }

  private static QuoteParameter newStockParam() {
    List<String> symbols = new ArrayList<>();
    symbols.add("AAPL");
    symbols.add("GOOG");
    return QuoteParamBuilder.instance().symbols(symbols).market(Market.US).build();
  }
}
