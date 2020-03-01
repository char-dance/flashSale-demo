package com.bytecollege.demo.flashSale;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class FlashSaleResp implements Serializable {
	private String itemId;
	private int campaignId;
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

	public FlashSaleResp(String itemId, int campaignId, String userId, String orderId, int code, String message) {
		this.itemId = itemId;
		this.campaignId = campaignId;
		this.userId = userId;
		this.orderId = orderId;
		this.code = code;
		this.message = message;
	}
}
