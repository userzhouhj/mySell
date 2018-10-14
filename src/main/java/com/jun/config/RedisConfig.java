package com.jun.config;

import com.jun.domain.ProductCategory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;


@Configuration
public class RedisConfig{

    @Bean
    public RedisTemplate<Object, ProductCategory> categoryRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        Jackson2JsonRedisSerializer<ProductCategory> jsr = new Jackson2JsonRedisSerializer<ProductCategory>(ProductCategory.class);
        RedisTemplate<Object, ProductCategory> template = new RedisTemplate<Object, ProductCategory>();

        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());


        return template;
    }

    @Bean
    public RedisCacheManager categoryRedisCacheManager(RedisTemplate<Object, ProductCategory> categoryRedisTemplate){
        RedisCacheManager cacheManager = new RedisCacheManager(categoryRedisTemplate);
        cacheManager.setUsePrefix(true);

        return cacheManager;
    }



}
