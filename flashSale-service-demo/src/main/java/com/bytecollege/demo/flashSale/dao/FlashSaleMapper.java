package com.bytecollege.demo.flashSale.dao;

import org.apache.ibatis.annotations.Select;

import com.bytecollege.demo.flashSale.dao.entity.CampaignEntity;
import com.bytecollege.demo.flashSale.dao.entity.ItemEntity;

//@Mapper
public interface FlashSaleMapper {

	@Select("select * from t_campaign_item where itemId = #{itemId}")
	ItemEntity getItem(String itemId);
	
	@Select("select * from t_campaign where id = #{campaignId}")
	CampaignEntity getCampaign(int campaignId);
}
