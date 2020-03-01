package com.next.demo.checkout;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class CheckoutReq implements Serializable {
	private String itemId;
	private String userId;

	public CheckoutReq(String itemId, String userId) {
		this.itemId = itemId;
		this.userId = userId;
	}
}
