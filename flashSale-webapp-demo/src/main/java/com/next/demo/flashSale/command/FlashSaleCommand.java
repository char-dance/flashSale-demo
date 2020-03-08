package com.next.demo.flashSale.command;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FlashSaleCommand {
	private String itemId;
	private int campaignId;
	private String userId;
	private LocalDateTime requestTime = LocalDateTime.now();
}
