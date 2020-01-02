package com.bytecollege.demo.flashSale;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flashSale")
public class FalshSaleController {

	private final AtomicLong counter = new AtomicLong();

	@Reference(version = "1.0.0", timeout = 3000)
	private FlashSaleService flashSaleService;

	@GetMapping("/checkout")
	public CheckoutModel checkout(@RequestParam(value = "name", defaultValue = "World") String name) {
		String greeting = flashSaleService.greet(name);
		long id = counter.incrementAndGet();
		return new CheckoutModel(id, greeting);
	}
}
