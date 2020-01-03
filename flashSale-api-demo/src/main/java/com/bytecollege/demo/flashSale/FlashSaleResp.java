package com.bytecollege.demo.flashSale;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressWarnings("serial")
public class FlashSaleResp implements Serializable {
	private long id;
	private String itemId;
	private String orderId;
	private boolean success;
	private LocalDateTime checkoutTime;

	public FlashSaleResp() {
		this.id = 8888;
		this.itemId = "8888";
		this.orderId = "8888";
		this.success = true;
		this.checkoutTime = LocalDateTime.now();
	}

	public FlashSaleResp(long id, String itemId, String orderId, boolean success,LocalDateTime checkoutTime) {
		this.id = id;
		this.itemId = itemId;
		this.orderId = orderId;
		this.success = success;
		this.checkoutTime = checkoutTime;
	}

	public long getId() {
		return id;
	}

	public String getItemId() {
		return itemId;
	}

	public String getOrderId() {
		return orderId;
	}

	public boolean isSuccess() {
		return success;
	}

	public LocalDateTime getCheckoutTime() {
		return checkoutTime;
	}

	@Override
	public String toString() {
		return "FlashSaleResp [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", success=" + success
				+ ", checkoutTime=" + checkoutTime + "]";
	}

}
