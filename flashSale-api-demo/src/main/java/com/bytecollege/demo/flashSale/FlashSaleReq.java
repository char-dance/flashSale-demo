package com.bytecollege.demo.flashSale;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class FlashSaleReq implements Serializable {
	private String itemId;
	private String userId;

	public FlashSaleReq(String itemId, String userId) {
		this.itemId = itemId;
		this.userId = userId;
	}
}
