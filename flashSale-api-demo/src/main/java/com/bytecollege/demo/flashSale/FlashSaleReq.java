package com.bytecollege.demo.flashSale;

import java.time.LocalDateTime;

public class FlashSaleReq {
	private long id;
	private String itemId;
	private LocalDateTime requestTime;

	public FlashSaleReq(long id, String itemId) {
		this.id = id;
		this.itemId = itemId;
		this.requestTime = LocalDateTime.now();
	}

	public long getId() {
		return id;
	}

	public String getItemId() {
		return itemId;
	}

	public LocalDateTime getRequestTime() {
		return requestTime;
	}

	@Override
	public String toString() {
		return "FlashSaleReq [id=" + id + ", itemId=" + itemId + ", requestTime=" + requestTime + "]";
	}

}
