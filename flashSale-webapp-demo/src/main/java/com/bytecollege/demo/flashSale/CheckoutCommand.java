package com.bytecollege.demo.flashSale;

public class CheckoutCommand {

	private long userId;
	private String itemId;

	public CheckoutCommand(long userId, String itemId) {
		this.userId = userId;
		this.itemId = itemId;
	}

	public long getUserId() {
		return userId;
	}

	public String getItemId() {
		return itemId;
	}

	@Override
	public String toString() {
		return "CheckoutCommand [userId=" + userId + ", itemId=" + itemId + "]";
	}

}
