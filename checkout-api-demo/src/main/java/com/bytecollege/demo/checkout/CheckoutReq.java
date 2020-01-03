package com.bytecollege.demo.checkout;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CheckoutReq implements Serializable {
	private long id;
	private String itemId;

	public CheckoutReq(long id, String itemId) {
		this.id = id;
		this.itemId = itemId;
	}

	public long getId() {
		return id;
	}

	public String getItemId() {
		return itemId;
	}

	@Override
	public String toString() {
		return "CheckoutReq [id=" + id + ", itemId=" + itemId + "]";
	}

}
