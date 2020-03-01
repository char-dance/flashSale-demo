package com.next.demo.flashSale;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan("com.next.demo.flashSale.dao")
@EnableDubbo(scanBasePackages="com.next.demo.flashSale")
@SpringBootApplication
public class FlashSaleServiceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlashSaleServiceDemoApplication.class, args);
	}
	
}
