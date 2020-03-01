package com.bytecollege.demo.flashSale;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FlashSaleCommand {
	private String itemId;
	private int campaignId;
	private String userId;
	private LocalDateTime requestTime = LocalDateTime.now();
}
