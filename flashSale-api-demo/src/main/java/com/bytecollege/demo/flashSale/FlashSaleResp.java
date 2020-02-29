package com.bytecollege.demo.flashSale;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class FlashSaleResp implements Serializable {
	private String itemId;
	private String userId;
	private String orderId;
	private int code;
	private String message;

	public FlashSaleResp() {
		this.itemId = "8888";
		this.orderId = "8888";
		this.message = "failed";
		this.code = -1;
	}

	public FlashSaleResp(String itemId, String userId, String orderId, int code, String message) {
		this.itemId = itemId;
		this.userId = userId;
		this.orderId = orderId;
		this.code = code;
		this.message = message;
	}
}
