package com.bytecollege.demo.flashSale;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class FlashSaleReq implements Serializable {
	private String itemId;
	private int userId;
	private int seqId;

	public FlashSaleReq(String itemId, int userId, int seqId) {
		this.itemId = itemId;
		this.userId = userId;
		this.seqId = seqId;
	}
}
