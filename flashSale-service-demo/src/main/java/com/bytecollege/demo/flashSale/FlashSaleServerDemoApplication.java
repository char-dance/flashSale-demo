package com.bytecollege.demo.flashSale;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan("com.bytecollege.demo.flashSale.dao")
@EnableDubbo(scanBasePackages="com.bytecollege.demo.flashSale")
@SpringBootApplication
public class FlashSaleServerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlashSaleServerDemoApplication.class, args);
	}
	
}
