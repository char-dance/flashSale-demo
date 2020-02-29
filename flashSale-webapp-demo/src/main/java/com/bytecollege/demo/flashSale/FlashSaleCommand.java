package com.bytecollege.demo.flashSale;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FlashSaleCommand {
	private String userId;
	private String itemId;
	private LocalDateTime requestTime = LocalDateTime.now();
}
