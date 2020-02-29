package com.bytecollege.demo.checkout;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class CheckoutReq implements Serializable {
	private String itemId;
	private int userId;
	private int seqId;

	public CheckoutReq(String itemId, int userId, int seqId) {
		this.itemId = itemId;
		this.userId = userId;
		this.seqId = seqId;
	}
}
