package com.zhangzhigang.tb.config;

import java.time.Duration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 配置类
 * Redis默认初始化 
 * @see org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
 * @author zhangzhigang
 */
@EnableCaching // 启用方法缓存
@Configuration
public class RedisConfiguration {
	
	private static RedisSerializer<Object> redisSerializer;
	
	static {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.CLOSE_CLOSEABLE, true)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisSerializer = jackson2JsonRedisSerializer;
	}
	
	/**
	 * 自定义RedisTemplate
	 * 
	 * @param factory 默认使用LettuceConnectionFactory
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

//		默认是多线程共享同一个连接
//		if (factory instanceof LettuceConnectionFactory) {
//			((LettuceConnectionFactory) factory).setShareNativeConnection(false);
//		}
		//设置序列化
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);
		StringRedisSerializer stringSerializer = new StringRedisSerializer();
		//key序列化
        redisTemplate.setKeySerializer(stringSerializer);
        //value序列化
        redisTemplate.setValueSerializer(redisSerializer);
        //Hash key序列化
        redisTemplate.setHashKeySerializer(stringSerializer);
        //Hash value序列化
        redisTemplate.setHashValueSerializer(redisSerializer);
		return redisTemplate;
	}
	
	/**
	 * 配置Redis为cache存储库,缓存方法
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
		redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(SerializationPair.fromSerializer(redisSerializer));
		redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofMinutes(5));
		return RedisCacheManager.builder(connectionFactory)
				.cacheDefaults(redisCacheConfiguration)
				.build();
	}
	
}