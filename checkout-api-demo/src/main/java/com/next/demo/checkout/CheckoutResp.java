package com.next.demo.checkout;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class CheckoutResp implements Serializable {
	private String itemId;
	private String userId;
	private String orderId;
	private int code;
	private String message;

	public CheckoutResp() {
		this.itemId = "8888";
		this.orderId = "8888";
		this.code = -1;
		this.message = "failed";
	}

	public CheckoutResp(String itemId, String userId, String orderId, int code, String message) {
		this.itemId = itemId;
		this.userId = userId;
		this.orderId = orderId;
		this.code = code;
		this.message = message;
	}

}
