package com.bytecollege.demo.flashSale;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.bytecollege.demo.checkout.CheckoutReq;
import com.bytecollege.demo.checkout.CheckoutResp;
import com.bytecollege.demo.checkout.CheckoutService;
import com.bytecollege.demo.flashSale.dao.FlashSaleMapper;
import com.bytecollege.demo.flashSale.dao.entity.CampaignEntity;
import com.bytecollege.demo.flashSale.dao.entity.ItemEntity;

@Service(version = "1.0.0", timeout = 3000)
public class FlashSaleServiceImpl implements FlashSaleService {
	private static final Log log = LogFactory.getLog(FlashSaleServiceImpl.class);

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	// @Autowired
	private RedisTemplate<String, Integer> flashSaleRedisTemplate;

	@Autowired
	private FlashSaleMapper flashSaleMapper;

	@Reference(version = "1.0.0", timeout = 3000)
	private CheckoutService checkoutService;

	private static final Map<String, Integer> STOCK_CACHE = new HashMap<>();

	@Override
	public FlashSaleResp check(FlashSaleReq req) {
		log.info("========================" + req);

		String itemId = req.getItemId();
		String userId = req.getUserId();

		int ret = checkStatus(itemId);
		if (ret <= 0) {
			return new FlashSaleResp(itemId, userId, "NoOrder", -1, "campaign is unavailable" + ret);
		}

		ret = checkStock(itemId);
		if (ret <= 0) {
			return new FlashSaleResp(itemId, userId, "NoOrder", -2, "stock is unavailable" + ret);
		}

		return new FlashSaleResp(itemId, userId, "NoOrder", 0, "campaign is available" + ret);
	}

	@Override
	public FlashSaleResp flash(FlashSaleReq req) {
		log.info("========================" + req);

		String itemId = req.getItemId();
		String userId = req.getUserId();

		// 1.检查
		int ret = checkStatus(itemId);
		if (ret <= 0) {
			return new FlashSaleResp(itemId, userId, "NoOrder", -1, "campaign is unavailable" + ret);
		}

		ret = checkStock(itemId);
		if (ret <= 0) {
			return new FlashSaleResp(itemId, userId, "NoOrder", -2, "stock is unavailable" + ret);
		}

		// 2.扣减库存 TODO:捕获异常并作出处理
		updateStock(itemId, 1);

		// 3.下单
		CheckoutResp checkoutResp = checkout(itemId, userId);
		if (checkoutResp.getCode() < 0) {
			// 恢复库存, 或者异步补偿
			updateStock(itemId, -1);
			return new FlashSaleResp(itemId, userId, checkoutResp.getOrderId(), -4, checkoutResp.getMessage());
		}

		return new FlashSaleResp(itemId, userId, checkoutResp.getOrderId(), 0, "flash sale success");
	}

	private int checkStatus(String itemId) {
		int ret = 0;
		String status = stringRedisTemplate.opsForValue().get("status");
		// Integer stock = flashSaleRedisTemplate.opsForValue().get(itemId);
		log.info("status from redis======================================" + status);
		if (status == null) {
			CampaignEntity campaignEntity = flashSaleMapper.getCampaign(1);
			log.info("status from mysql======================================" + campaignEntity);
			if (campaignEntity.getStatus() == 0) {
				ret = -1;
			}

			status = campaignEntity.getStatus() + "";
			stringRedisTemplate.opsForValue().set("status", status);
		}

		return ret;
	}

	private int checkStock(String itemId) {
		int ret = 0;
		String stock = stringRedisTemplate.opsForValue().get(itemId);
		// Integer stock = flashSaleRedisTemplate.opsForValue().get(itemId);
		log.info("stock from redis======================================" + stock);
		if (stock == null) {
			ItemEntity itemEntity = flashSaleMapper.getItem(itemId);
			log.info("stock from mysql======================================" + stock);
			if (itemEntity.getStock() <= 0) {
				ret = -1;
			}

			stock = itemEntity.getStock() + "";
			stringRedisTemplate.opsForValue().set(itemId, stock);
		}

		return ret;
	}

	private int updateStock(String itemId, int count) {
		stringRedisTemplate.delete(itemId);
		flashSaleMapper.updateStock(itemId, count);
		return 0;
	}

	private CheckoutResp checkout(String itemId, String userId) {
		CheckoutReq checkoutReq = new CheckoutReq(itemId, userId);
		CheckoutResp checkoutResp = checkoutService.checkout(checkoutReq);
		log.info("========================" + checkoutReq);
		log.info("========================" + checkoutResp);
		return checkoutResp;
	}

}
