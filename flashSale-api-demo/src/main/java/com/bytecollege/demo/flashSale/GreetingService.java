package com.bytecollege.demo.flashSale;

public interface GreetingService {

	default String greet(int time) {
		return "Good morning!";
	}

}
