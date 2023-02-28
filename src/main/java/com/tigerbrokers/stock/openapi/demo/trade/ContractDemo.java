package com.tigerbrokers.stock.openapi.demo.trade;

import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractModel;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractsModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.request.contract.ContractRequest;
import com.tigerbrokers.stock.openapi.client.https.request.contract.ContractsRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse;
import com.tigerbrokers.stock.openapi.client.https.response.contract.ContractResponse;
import com.tigerbrokers.stock.openapi.client.https.response.contract.ContractsResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.Right;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.util.builder.AccountParamBuilder;
import com.tigerbrokers.stock.openapi.demo.TigerOpenClientConfig;
import java.util.List;

/**
 * Description: stock/option/warrant/CBBC contract interface
 * Created by lijiawen on 2018/05/31.
 */
public class ContractDemo {

  private static TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(
      TigerOpenClientConfig.getDefaultClientConfig());

  public String getContact(String account, String symbol) {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.CONTRACT);
    String bizContent = AccountParamBuilder.instance()
        .account(account)
        .symbol(symbol)
        .buildJson();
    request.setBizContent(bizContent);
    TigerHttpResponse response = client.execute(request);
    return response.getData();
  }

  public List<ContractItem> getContacts(List<String> symbols) {
    ContractsRequest contractsRequest = ContractsRequest.newRequest(new ContractsModel(symbols));
    ContractsResponse contractsResponse = client.execute(contractsRequest);
    return contractsResponse.getItems();
  }

  public ContractItem getStockContact(String symbol) {
    ContractModel model = new ContractModel(symbol);
    ContractRequest contractRequest = ContractRequest.newRequest(model);
    ContractResponse contractResponse = client.execute(contractRequest);
    return contractResponse.getItem();
  }

  public ContractItem getOptionContract(String symbol, String expiry, double strike, Right right) {
    ContractModel model =
        new ContractModel(symbol, SecType.OPT.name(), Currency.USD.name(), expiry, strike, right.name());
    ContractResponse contractResponse = client.execute(ContractRequest.newRequest(model));
    return contractResponse.getItem();
  }

  public ContractItem getWarrantContract(String symbol, String expiry, double strike, Right right) {
    ContractModel contractModel =
        new ContractModel(symbol, SecType.WAR.name(), Currency.HKD.name(), expiry, strike, right.name());
    ContractResponse contractResponse = client.execute(ContractRequest.newRequest(contractModel));
    return contractResponse.getItem();
  }

  public ContractItem getCbbcContract(String symbol, String expiry, double strike, Right right) {
    ContractModel contractModel =
        new ContractModel(symbol, SecType.IOPT.name(), Currency.HKD.name(), expiry, strike, right.name());
    ContractResponse contractResponse = client.execute(ContractRequest.newRequest(contractModel));
    return contractResponse.getItem();
  }
}
