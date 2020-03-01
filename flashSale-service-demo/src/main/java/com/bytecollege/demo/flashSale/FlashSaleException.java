package com.bytecollege.demo.flashSale;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class FlashSaleException extends Exception {
	private int code;

	private String itemId;
	private int campaignId;
	private String userId;
	private String orderId;

	public FlashSaleException() {
		super();
		code = -1;
	}

	public FlashSaleException(int code, String message) {
		super(message);
		this.code = code;
	}

	public FlashSaleException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public FlashSaleException(int code, String message, String itemId, int campaignId, String userId, String orderId) {
		super(message);
		this.code = code;
		this.itemId = itemId;
		this.campaignId = campaignId;
		this.userId = userId;
		this.orderId = orderId;
	}

	public FlashSaleException(int code, String message, String itemId, int campaignId, String userId, String orderId,
			Throwable cause) {
		super(message, cause);
		this.code = code;
		this.code = code;
		this.itemId = itemId;
		this.campaignId = campaignId;
		this.userId = userId;
		this.orderId = orderId;
	}

}
