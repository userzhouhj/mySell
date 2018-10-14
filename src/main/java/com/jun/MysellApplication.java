package com.jun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(value = "com.jun.dao")
@ConfigurationProperties
@EnableCaching
public class MysellApplication {

    public static void main(String[] args) {
        SpringApplication.run(MysellApplication.class, args);
    }
}
