package com.next.demo.checkout;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo(scanBasePackages = "com.next.demo.checkout")
@SpringBootApplication
public class CheckoutServiceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckoutServiceDemoApplication.class, args);
	}

}
