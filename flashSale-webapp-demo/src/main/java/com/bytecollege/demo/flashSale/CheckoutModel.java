package com.bytecollege.demo.flashSale;

public class CheckoutModel {

	private long id;
	private String itemId;
	private String resultMsg;

	public CheckoutModel(long id, String itemId, String resultMsg) {
		this.id = id;
		this.itemId = itemId;
		this.resultMsg = resultMsg;
	}

	public long getId() {
		return id;
	}

	public String getItemId() {
		return itemId;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	@Override
	public String toString() {
		return "CheckoutModel [id=" + id + ", itemId=" + itemId + ", resultMsg=" + resultMsg + "]";
	}
}
