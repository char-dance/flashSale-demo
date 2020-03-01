package com.next.demo.flashSale.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.next.demo.flashSale.dao.entity.CampaignEntity;
import com.next.demo.flashSale.dao.entity.ItemEntity;

//@Mapper
public interface FlashSaleMapper {

	@Select("select * from t_campaign where id = #{campaignId}")
	CampaignEntity getCampaign(int campaignId);

	@Select("select * from t_campaign_item where item_id = #{itemId}")
	ItemEntity getItem(String itemId);

	@Update("update t_campaign_item set stock = stock - #{count} where item_id = #{itemId} and stock >= #{count}")
	int updateStock(String itemId, int count);
}
