package com.bytecollege.demo.flashSale.dao.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class CampaignEntity {

	private int id;
	private String name;
	private Date start_time;
	private Date end_time;
	private int  status;
}
