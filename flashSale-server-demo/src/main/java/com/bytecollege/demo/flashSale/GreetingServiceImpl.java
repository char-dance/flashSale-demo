package com.bytecollege.demo.flashSale;

import org.springframework.stereotype.Service;

@Service("greetingService")
public class GreetingServiceImpl implements GreetingService {
	
	@Override
	public String greet(int time) {
		// 根据时间返回morning，afternoon，evening，night
		return "Good " + time;
	}
}
