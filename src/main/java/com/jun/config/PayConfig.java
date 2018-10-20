package com.jun.config;


import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayConfig {

    @Autowired
    private WeChatParamterConfig config;

    @Bean
    public BestPayServiceImpl bestPayService(){
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();

        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId(config.getAppId());
        wxPayH5Config.setAppSecret(config.getAppSecret());
        wxPayH5Config.setKeyPath(config.getKeyPath());
        wxPayH5Config.setMchId(config.getMchId());
        wxPayH5Config.setMchKey(config.getMchKey());
        wxPayH5Config.setNotifyUrl(config.getNotifyUrl());

        bestPayService.setWxPayH5Config(wxPayH5Config);

        return bestPayService;
    }
}
