package com.bytecollege.demo.checkout;

import java.time.LocalDateTime;

public class CheckoutResp {
	private long id;
	private String itemId;
	private String orderId;
	private boolean success;
	private LocalDateTime checkoutTime;

	public CheckoutResp() {
		this.id = 8888;
		this.itemId = "8888";
		this.orderId = "8888";
		this.success = true;
		this.checkoutTime = LocalDateTime.now();
	}

	public CheckoutResp(long id, String itemId, String orderId, boolean success) {
		this.id = id;
		this.itemId = itemId;
		this.orderId = orderId;
		this.success = success;
		this.checkoutTime = LocalDateTime.now();
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
		return "CheckoutResp [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", success=" + success
				+ ", checkoutTime=" + checkoutTime + "]";
	}

}
