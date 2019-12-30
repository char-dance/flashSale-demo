package com.bytecollege.demo.flashSale;

import org.apache.dubbo.config.annotation.Service;

@Service(version = "1.0.0", timeout = 3000)
public class GreetingServiceImpl implements GreetingService {

	@Override
	public String greet(int time) {
		// 根据时间返回morning，afternoon，evening，night
		return "Good " + time;
	}
}
