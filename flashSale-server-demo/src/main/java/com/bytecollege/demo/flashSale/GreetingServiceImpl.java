package com.bytecollege.demo.flashSale;

import java.time.LocalTime;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@Service(version = "1.0.0", timeout = 3000)
public class GreetingServiceImpl implements GreetingService {
	private static final String TEMPLATE = "Good %s, %s!";

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	// @Autowired
	// RedisReposiyory redisReposiyory;

	@Override
	public String greet(String name) {
		// redisReposiyory.getSet();
		System.out.println("redisTemplate======================================" + redisTemplate);
		ValueOperations<String, String> vo = redisTemplate.opsForValue();
		System.out.println("vo======================================" + vo);
		vo.set("foo", "bar");
		System.out.println("redisTemplate2======================================" + redisTemplate);
		//String value = redisTemplate.opsForValue().get("foo");
		//System.out.println("value======================================" + value);

		// 根据时间返回morning，noon,afternoon，evening，night
		int hour = LocalTime.now().getHour();
		return String.format(TEMPLATE, generate(hour), name);
	}

	private String generate(int hour) {
		String ret = "morning";
		if (hour >= 11 && hour < 13) {
			return "noon"; // 11-13
		} else if (hour >= 13 && hour < 18) {
			return "afternoon"; // 13-18
		} else if (hour >= 18 && hour < 21) {
			return "evening"; // 18-21
		} else if (hour >= 21 && hour <= 23) {
			return "night"; // 21-24
		}
		return ret;
	}
}
