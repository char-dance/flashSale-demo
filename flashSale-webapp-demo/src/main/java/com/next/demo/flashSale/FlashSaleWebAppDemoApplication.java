package com.next.demo.flashSale;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo(scanBasePackages = "com.next.demo.flashSale")
@SpringBootApplication
public class FlashSaleWebAppDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlashSaleWebAppDemoApplication.class, args);
	}

}
