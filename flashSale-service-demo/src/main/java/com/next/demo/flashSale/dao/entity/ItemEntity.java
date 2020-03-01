package com.next.demo.flashSale.dao.entity;

import lombok.Data;

@Data
public class ItemEntity {

	private int id;
	private String itemId;
	private int stock;
	private int campaignId;
	private int  status;
}
