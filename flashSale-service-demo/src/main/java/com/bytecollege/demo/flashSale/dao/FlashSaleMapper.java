package com.bytecollege.demo.flashSale.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bytecollege.demo.flashSale.dao.entity.CampaignEntity;
import com.bytecollege.demo.flashSale.dao.entity.ItemEntity;

//@Mapper
public interface FlashSaleMapper {

	@Select("select * from t_campaign where id = #{campaignId}")
	CampaignEntity getCampaign(int campaignId);

	@Select("select * from t_campaign_item where item_id = #{itemId}")
	ItemEntity getItem(String itemId);

	@Update("update t_campaign_item set stock = stock - #{stock} where item_id = #{itemId} and stock >= #{stock}")
	void updateStock(String itemId, int stock);
}