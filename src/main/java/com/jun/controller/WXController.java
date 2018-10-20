package com.jun.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weixin")
public class WXController {
    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code){
        System.out.println(code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxf9192066845a5d07&secret=debc8ac1e607ccf8367d63a3d6bb7c7d&code="+code+"&grant_type=authorization_code";

        RestTemplate restTemplate = new RestTemplate();
        String rest = restTemplate.getForObject(url,String.class);
        System.out.println(rest);

    }

}
