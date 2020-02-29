package com.bytecollege.demo.flashSale;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class FlashSaleResp implements Serializable {
	private String itemId;
	private int userId;
	private String orderId;
	private boolean success;
	private int seqId;

	public FlashSaleResp() {
		this.seqId = 8888;
		this.itemId = "8888";
		this.orderId = "8888";
		this.success = true;
	}

	public FlashSaleResp(String itemId, int userId, String orderId, boolean success, int seqId) {
		this.itemId = itemId;
		this.userId = userId;
		this.orderId = orderId;
		this.success = success;
		this.seqId = seqId;
	}
}
