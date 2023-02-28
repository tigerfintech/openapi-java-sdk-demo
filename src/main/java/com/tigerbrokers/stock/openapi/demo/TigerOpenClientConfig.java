package com.tigerbrokers.stock.openapi.demo;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.License;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.FileUtil;

/**
 * @author liutongping
 * @version 1.0
 * @description:
 * @date 2021/11/8 上午11:35
 */
public class TigerOpenClientConfig {
  static {
    ApiLogger.setEnabled(true, "/data0/logs/");
    ClientConfig clientConfig = ClientConfig.DEFAULT_CONFIG;
    clientConfig.configFilePath = "/data0/tiger_config";
    //clientConfig.isSslSocket = true;
    //clientConfig.isAutoGrabPermission = true;
    //clientConfig.isAutoRefreshToken = true;
    //clientConfig.timeZone = TimeZoneId.Shanghai;
    //clientConfig.language = Language.en_US;
    //clientConfig.privateKey = FileUtil.readPrivateKey("/Users/tiger/.ssh/rsa_private_key_test.pem");
    //clientConfig.secretKey = "xxxxxx";
  }

  public static ClientConfig getDefaultClientConfig() {
    return ClientConfig.DEFAULT_CONFIG;
  }
}
