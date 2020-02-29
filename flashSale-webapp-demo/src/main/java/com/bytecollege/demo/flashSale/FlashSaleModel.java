package com.bytecollege.demo.flashSale;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FlashSaleModel {
	private String itemId;
	private int userId;
	private String resultMsg;
	private LocalDateTime responseTime;
	private int seqId;

	public FlashSaleModel(String itemId, int userId, String resultMsg, int seqId) {
		this.itemId = itemId;
		this.userId = userId;
		this.resultMsg = resultMsg;
		this.responseTime = LocalDateTime.now();
		this.seqId = seqId;
	}

}
