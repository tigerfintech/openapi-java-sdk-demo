package com.tigerbrokers.stock.openapi.demo.quote;

import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionBriefItem;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionChainItem;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionExpirationItem;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionKlineItem;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionTradeTickItem;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.WarrantFilterItem;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.WarrantQuoteItem;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionCommonModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionKlineModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionBriefQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionChainQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionExpirationQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionKlineQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionTradeTickQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.WarrantFilterRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.WarrantQuoteRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteContractRequest;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionBriefResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionChainResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionExpirationResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionKlineResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionTradeTickResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.WarrantFilterResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.WarrantQuoteResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteContractResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.WarrantType;
import com.tigerbrokers.stock.openapi.demo.TigerOpenClientConfig;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class OptionDemo {

  private static TigerHttpClient client =
      TigerHttpClient.getInstance().clientConfig(TigerOpenClientConfig.getDefaultClientConfig());

  public List<OptionExpirationItem>  getOptionExpiration(List<String> symbols) {
    OptionExpirationResponse response = client.execute(new OptionExpirationQueryRequest(symbols));
    return response.getOptionExpirationItems();
  }

  public List<OptionBriefItem> getOptionBrief(String symbol,String optionRight,String strike,String expiry) {
    OptionCommonModel model = new OptionCommonModel();
    model.setSymbol(symbol);
    model.setRight(optionRight);
    model.setStrike(strike);
    model.setExpiry(expiry);
    OptionBriefResponse response = client.execute(OptionBriefQueryRequest.of(model));
    return response.getOptionBriefItems();
  }

  public List<OptionChainItem>  getOptionChain(String symbol,String expiry) {
    OptionChainModel model = new OptionChainModel();
    model.setSymbol(symbol);
    model.setExpiry(expiry);

    OptionChainResponse response = client.execute(OptionChainQueryRequest.of(model));
    return response.getOptionChainItems();
  }

  public List<OptionKlineItem>  getOptionKline(String symbol,String optionRight,String strike,String expiry,String beginTime,String endTime) {
    OptionKlineModel model = new OptionKlineModel();
    model.setSymbol(symbol);
    model.setRight(optionRight);
    model.setStrike(strike);
    model.setExpiry(expiry);
    model.setBeginTime(beginTime);
    model.setEndTime(endTime);

    OptionKlineResponse response = client.execute(OptionKlineQueryRequest.of(model));
    return response.getKlineItems();
  }

  public List<OptionTradeTickItem>  getOptionTradeTick(String symbol,String optionRight,String strike,String expiry) {
    OptionCommonModel model = new OptionCommonModel();
    model.setSymbol(symbol);
    model.setRight(optionRight);
    model.setStrike(strike);
    model.setExpiry(expiry);

    OptionTradeTickResponse response = client.execute(OptionTradeTickQueryRequest.of(model));
    return response.getOptionTradeTickItems();
  }

  public List<ContractItem> getQuoteOptionContract(String symbol, String expiry) {
    QuoteContractResponse response = client.execute(QuoteContractRequest.newRequest(symbol, SecType.OPT, expiry));
    return response.getContractItems();
  }

  public WarrantFilterItem getWarrantsAndCBBC(String symbol,
      WarrantType type, String issuerName) {
    WarrantFilterRequest request = WarrantFilterRequest.newRequest(symbol);
    request.warrantType(type);
    request.issuerName(issuerName);
    WarrantFilterResponse response = client.execute(request);
    if (response.isSuccess()) {
      return response.getItem();
    }
    return null;
  }

  public WarrantQuoteItem getWarrantQuote(List<String> symbols) {
    WarrantQuoteRequest request = WarrantQuoteRequest.newRequest(symbols);
    WarrantQuoteResponse response = client.execute(request);
    return response.getItem();
  }
}
