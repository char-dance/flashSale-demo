package com.bytecollege.demo.flashSale;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class FlashSaleReq implements Serializable {
	private String itemId;
	private int campaignId;
	private String userId;

	public FlashSaleReq(String itemId, int campaignId, String userId) {
		this.itemId = itemId;
		this.campaignId = campaignId;
		this.userId = userId;
	}
}
