package com.bytecollege.demo.checkout;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class CheckoutResp implements Serializable {
	private String itemId;
	private int userId;
	private String orderId;
	private boolean success;
	private int seqId;

	public CheckoutResp() {
		this.seqId = 8888;
		this.itemId = "8888";
		this.orderId = "8888";
		this.success = true;
	}

	public CheckoutResp(String itemId, int userId, String orderId, boolean success, int id) {
		this.itemId = itemId;
		this.userId = userId;
		this.orderId = orderId;
		this.success = success;
		this.seqId = id;
	}

}
