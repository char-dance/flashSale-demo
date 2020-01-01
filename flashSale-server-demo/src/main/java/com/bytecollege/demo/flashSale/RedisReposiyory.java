package com.bytecollege.demo.flashSale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class RedisReposiyory {
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	public String getSet(){
		System.out.println("redisTemplate000======================================" + redisTemplate);
		ValueOperations<String, String> vo = redisTemplate.opsForValue();
		System.out.println("vo000======================================" + vo);
		
		vo.set("foo", "bar");
		System.out.println("redisTemplate002======================================" + redisTemplate);
		
		String value = redisTemplate.opsForValue().get("foo");
	    System.out.println("value======================================" + value);
	    
	    return value;
	}
}
