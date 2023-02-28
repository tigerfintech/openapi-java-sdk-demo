package com.tigerbrokers.stock.openapi.demo.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.PrimeAssetItem;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.request.contract.ContractRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.PrimeAssetRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse;
import com.tigerbrokers.stock.openapi.client.https.response.contract.ContractResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PrimeAssetResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderStatus;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.util.builder.AccountParamBuilder;
import com.tigerbrokers.stock.openapi.demo.TigerOpenClientConfig;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/07/06.
 */
public class AccountDemo {

  private static ClientConfig clientConfig = TigerOpenClientConfig.getDefaultClientConfig();
  private static TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(clientConfig);

  public ContractItem getContract(String symbol) {
    ContractRequest contractRequest = ContractRequest.newRequest(new ContractModel(symbol));
    ContractResponse contractResponse = client.execute(contractRequest);
    return contractResponse.getItem();
  }

  public String getAccountInfo(String account) {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.ACCOUNTS);
    String bizContent = AccountParamBuilder.instance()
        .account(account)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    return response.getData();
  }

  public PrimeAssetItem getAsset(String account, Currency baseCurrency) {
    PrimeAssetRequest assetRequest = PrimeAssetRequest.buildPrimeAssetRequest(account, baseCurrency);
    PrimeAssetResponse primeAssetResponse = client.execute(assetRequest);
    return primeAssetResponse.getItem();
  }

  public String getGlobalAccountAsset(String account) {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.ASSETS);
    String bizContent = AccountParamBuilder.instance()
        .account(account)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    return response.getData();
  }

  public String queryPosition(String account, String symbol, Market market, SecType secType, Currency currency) {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.POSITIONS);
    String bizContent = AccountParamBuilder.instance()
        .account(account)
        .currency(currency)
        .market(market)
        .symbol(symbol)
        .secType(secType)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    return response.getData();
  }

  public String getOrders(String account, String beginDate, String endDate, SecType secType, Market market) {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.ORDERS);
    List<String> states = new ArrayList<>();
    states.add(OrderStatus.Submitted.name());
    states.add(OrderStatus.Filled.name());

    String bizContent = AccountParamBuilder.instance()
        .account(account)
        .startDate(beginDate)
        .endDate(endDate)
        .secType(secType)
        .market(market)
        .states(states)
        .isBrief(false)
        .buildJson();
    request.setBizContent(bizContent);

    TigerHttpResponse response = client.execute(request);
    return response.getData();
  }

  public static void main(String[] args) {
    AccountDemo accountDemo = new AccountDemo();
    PrimeAssetItem asset = accountDemo.getAsset(clientConfig.defaultAccount, Currency.USD);
    System.out.println(asset.getSegments().get(0).getCurrency());
  }
}
