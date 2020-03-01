package com.next.demo.flashSale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	@Bean("redisTemplate")
	public RedisTemplate<String, Integer> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Integer> template = new RedisTemplate<String, Integer>();
		template.setConnectionFactory(factory);

		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		template.setKeySerializer(stringRedisSerializer);
		//template.setValueSerializer(stringRedisSerializer);
		//template.setHashKeySerializer(stringRedisSerializer);
		//template.setHashValueSerializer(stringRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}
}
