package com.bytecollege.demo.flashSale;

import java.time.LocalTime;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.bytecollege.demo.checkout.CheckoutService;
import com.bytecollege.demo.flashSale.dao.GreetingEntity;
import com.bytecollege.demo.flashSale.dao.GreetingMapper;

@Service(version = "1.0.0", timeout = 3000)
public class FlashSaleServiceImpl implements FlashSaleService {
	private static final String TEMPLATE = "Good %s, %s! This is %s";

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private GreetingMapper greetingMapper;

	@Reference(version = "1.0.0", timeout = 3000)
	private CheckoutService checkoutService;

	@Override
	public String greet(String name) {
		redisTemplate.opsForValue().set("foo", "bar");
		String value = redisTemplate.opsForValue().get("foo");
		System.out.println("value======================================" + value);

		GreetingEntity greetingEntity = greetingMapper.getContentById(1);
		String content = greetingEntity.getContent();
		System.out.println("content======================================" + content);

		// 根据时间返回morning，noon,afternoon，evening，night
		int hour = LocalTime.now().getHour();
		return String.format(TEMPLATE, generate(hour), name, content);
	}
	
	@Override
	public FlashSaleResp checkout(FlashSaleReq req) {
		return new FlashSaleResp();
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
