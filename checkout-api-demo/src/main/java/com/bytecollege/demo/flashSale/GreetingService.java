package com.bytecollege.demo.flashSale;

public interface GreetingService {

	default String greet(String name) {
		return "Good Goog morning, " + name;
	}

}
