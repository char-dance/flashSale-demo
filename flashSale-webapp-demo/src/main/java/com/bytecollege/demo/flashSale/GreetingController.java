package com.bytecollege.demo.flashSale;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "%s, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Reference(version = "1.0.0", timeout = 3000)
	private GreetingService greetingService;

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		String greeting = greetingService.greet(10);
		return new Greeting(counter.incrementAndGet(), String.format(template, greeting, name));
	}
}
