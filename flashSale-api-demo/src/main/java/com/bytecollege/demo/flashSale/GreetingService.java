package com.bytecollege.demo.flashSale;

public interface GreetingService {

	default String greet(String name) {
		return "Good morning!" + name;
	}

}
