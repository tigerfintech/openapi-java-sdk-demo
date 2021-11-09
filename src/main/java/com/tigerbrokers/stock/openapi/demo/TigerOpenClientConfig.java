package com.tigerbrokers.stock.openapi.demo;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liutongping
 * @version 1.0
 * @description:
 * @date 2021/11/8 上午11:35
 */
public class TigerOpenClientConfig {
  static {
    ClientConfig clientConfig = ClientConfig.DEFAULT_CONFIG;
    //clientConfig.serverUrl = "https://openapi.itiger.com/gateway";
    //clientConfig.socketServerUrl = "wss://openapi.itiger.com:8887/stomp";
    clientConfig.tigerId = "your_tigerId";
    clientConfig.defaultAccount = "default_account";
    clientConfig.privateKey = clientConfig.readPrivateKey("/Users/tiger/.ssh/rsa_public_key_test.pem");
    //clientConfig.privateKey = "your_private_key";
    clientConfig.secretKey = "xxxxxx";
  }
  public static ClientConfig getDefaultClientConfig() {
    return ClientConfig.DEFAULT_CONFIG;
  }
}
