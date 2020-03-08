package com.next.demo.flashSale.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FlashSaleModel {
	private String itemId;
	private String userId;
	private String orderId;
	private LocalDateTime responseTime;
	private int code;
	private String message;

	public FlashSaleModel(String itemId, String userId, String orderId, int code, String message) {
		this.itemId = itemId;
		this.userId = userId;
		this.orderId = orderId;
		this.responseTime = LocalDateTime.now();
		this.code = code;
		this.message = message;
	}

}
