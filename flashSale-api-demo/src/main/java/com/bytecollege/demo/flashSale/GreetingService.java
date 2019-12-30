package com.bytecollege.demo.flashSale;

public interface GreetingService {

	default public String greet(String time) {
		return "Good " + time;
	}

}
