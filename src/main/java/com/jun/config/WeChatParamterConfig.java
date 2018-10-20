package com.jun.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;

@ConfigurationProperties(prefix = "WeChat")
@Data
@Component
public class WeChatParamterConfig {

    private String appId;
    private String appSecret;

    private String mchId;//商品号
    private String mchKey;//商户密钥
    private String keyPath;//商户证书路径

    private String notifyUrl;
}
