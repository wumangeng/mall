package com.mallcloud.mall.product.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * 自定义Cache缓存策略
 * */
@Configuration
public class CaheConfig {

	@Bean
	RedisCacheConfiguration redisCacheConfiguration(){
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
		// 设置key为String
		config = config.serializeKeysWith(RedisSerializationContext.string().getKeySerializationPair());
		// 设置value json序列化格式
		config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericFastJsonRedisSerializer()));
		// 缓存数据保存1小时
		config = config.entryTtl(Duration.ofHours(1));
		return config;
	}

}
