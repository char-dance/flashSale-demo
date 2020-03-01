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
import org.springframework.util.StringUtils;

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

		try {
			preCheck(req);
		} catch (FlashSaleException e) {
			log.error("check failed", e);
			return new FlashSaleResp(e.getItemId(), e.getCampaignId(), e.getUserId(), e.getOrderId(), e.getCode(),
					e.getMessage());
		}

		return new FlashSaleResp(req.getItemId(), req.getCampaignId(), req.getUserId(), "NoOrder", 0,
				"campaign is available");
	}

	@Override
	public FlashSaleResp flash(FlashSaleReq req) {
		log.info("========================" + req);

		try {
			preCheck(req);
			reduceStock(req);
			checkout(req);
		} catch (FlashSaleException e) {
			log.error("flash failed", e);
			return new FlashSaleResp(e.getItemId(), e.getCampaignId(), e.getUserId(), e.getOrderId(), e.getCode(),
					e.getMessage());
		}

		return new FlashSaleResp(req.getItemId(), req.getCampaignId(), req.getUserId(), "", 0, "flash sale success");
	}

	private void preCheck(FlashSaleReq req) throws FlashSaleException {
		String itemId = req.getItemId();
		int campaignId = req.getCampaignId();
		String userId = req.getUserId();

		// 1.从缓存中读取活动状态
		String status = stringRedisTemplate.opsForValue().get("status");
		// Integer stock = flashSaleRedisTemplate.opsForValue().get(itemId);
		log.info("status from cache======================================" + status);

		if (StringUtils.isEmpty(status)) {
			// 2.缓存未命中，从数据库中读取
			CampaignEntity campaignEntity = flashSaleMapper.getCampaign(campaignId);
			log.info("status from database======================================" + campaignEntity);

			if (campaignEntity.getStatus() == 0) {
				// 3.如果状态不可用，返回
				throw new FlashSaleException(-1, "campaign is unavailable", itemId, campaignId, "NoOrder", userId);
			}

			// 4.状态值写入缓存
			status = campaignEntity.getStatus() + "";
			stringRedisTemplate.opsForValue().set("status", status);
		}

		// 1.从缓存中读取商品库存
		String stock = stringRedisTemplate.opsForValue().get(itemId);
		// Integer stock = flashSaleRedisTemplate.opsForValue().get(itemId);
		log.info("stock from cache======================================" + stock);

		if (StringUtils.isEmpty(stock)) {
			// 2.缓存未命中，从数据库中读取
			ItemEntity itemEntity = flashSaleMapper.getItem(itemId);
			log.info("stock from database======================================" + stock);

			if (itemEntity.getStock() <= 0) {
				// 3.如果状态不可用，返回
				throw new FlashSaleException(-2, "stock is unavailable", itemId, campaignId, "NoOrder", userId);
			}

			// 4.库存值写入缓存
			stock = itemEntity.getStock() + "";
			stringRedisTemplate.opsForValue().set(itemId, stock);
		}
	}

	private void reduceStock(FlashSaleReq req) throws FlashSaleException {
		String itemId = req.getItemId();
		int campaignId = req.getCampaignId();
		String userId = req.getUserId();

		try {
			// TODO:区分异常
			stringRedisTemplate.delete(itemId);
			flashSaleMapper.updateStock(itemId, 1);
		} catch (Exception e) {
			// 扣减库存失败，返回
			throw new FlashSaleException(-3, "reduce stock failed", itemId, campaignId, "NoOrder", userId, e);
		}
	}

	private void checkout(FlashSaleReq req) throws FlashSaleException {
		String itemId = req.getItemId();
		int campaignId = req.getCampaignId();
		String userId = req.getUserId();

		// TODO:捕获并区分异常
		CheckoutReq checkoutReq = new CheckoutReq(itemId, userId);
		CheckoutResp checkoutResp = checkoutService.checkout(checkoutReq);
		log.info("========================" + checkoutReq);
		log.info("========================" + checkoutResp);
		if (checkoutResp.getCode() < 0) {
			// 恢复库存, 后面改异步重试
			stringRedisTemplate.delete(itemId);
			flashSaleMapper.updateStock(itemId, -1);

			// 下单失败，返回
			throw new FlashSaleException(-4, checkoutResp.getMessage(), itemId, campaignId, checkoutResp.getOrderId(),
					userId);
		}
	}
}
