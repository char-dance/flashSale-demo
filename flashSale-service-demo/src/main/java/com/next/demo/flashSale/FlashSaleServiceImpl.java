package com.next.demo.flashSale;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.next.demo.checkout.CheckoutReq;
import com.next.demo.checkout.CheckoutResp;
import com.next.demo.checkout.CheckoutService;
import com.next.demo.flashSale.FlashSaleReq;
import com.next.demo.flashSale.FlashSaleResp;
import com.next.demo.flashSale.FlashSaleService;
import com.next.demo.flashSale.dao.FlashSaleMapper;
import com.next.demo.flashSale.dao.entity.CampaignEntity;
import com.next.demo.flashSale.dao.entity.ItemEntity;

@Service(version = "1.0.0", timeout = 3000)
public class FlashSaleServiceImpl implements FlashSaleService {
	private static final Log log = LogFactory.getLog(FlashSaleServiceImpl.class);

	// @Autowired
	// private StringRedisTemplate stringRedisTemplate;

	// 这里不能用@Autowired，因为依据名字注入
	@Resource(name = "redisTemplate")
	private RedisTemplate<String, Integer> flashSaleRedisTemplate;

	@Autowired
	private FlashSaleMapper flashSaleMapper;

	@Reference(version = "1.0.0", timeout = 3000)
	private CheckoutService checkoutService;

	@Override
	public FlashSaleResp check(FlashSaleReq req) {
		log.info("check========================" + req);

		try {
			preCheck(req);
		} catch (FlashSaleException e) {
			log.error("check failed", e);
			return new FlashSaleResp(e.getItemId(), e.getCampaignId(), e.getUserId(), e.getOrderId(), e.getCode(),
					e.getMessage());
		} catch (Exception e) {
			log.error("system error", e);
			return new FlashSaleResp(req.getItemId(), req.getCampaignId(), req.getUserId(), "NoOrder", -100,
					e.getMessage());
		}

		return new FlashSaleResp(req.getItemId(), req.getCampaignId(), req.getUserId(), "NoOrder", 0,
				"campaign is available");
	}

	@Override
	public FlashSaleResp flash(FlashSaleReq req) {
		log.info("flash========================" + req);

		try {
			// 1.检查秒杀条件
			preCheck(req);

			// 2.扣减库存
			reduceStock(req);

			// 3.下单
			checkout(req);
		} catch (FlashSaleException e) {
			log.error("flash failed", e);
			return new FlashSaleResp(e.getItemId(), e.getCampaignId(), e.getUserId(), e.getOrderId(), e.getCode(),
					e.getMessage());
		} catch (Exception e) {
			log.error("system error", e);
			return new FlashSaleResp(req.getItemId(), req.getCampaignId(), req.getUserId(), "NoOrder", -101,
					e.getMessage());
		}

		return new FlashSaleResp(req.getItemId(), req.getCampaignId(), req.getUserId(), "", 0, "flash sale success");
	}

	private void preCheck(FlashSaleReq req) throws FlashSaleException {
		String itemId = req.getItemId();
		int campaignId = req.getCampaignId();
		String userId = req.getUserId();

		// 1.1.从缓存中读取活动状态
		Integer status = flashSaleRedisTemplate.opsForValue().get("status");
		// Integer stock = flashSaleRedisTemplate.opsForValue().get(itemId);
		log.info("status from cache " + status);

		// 1.2.缓存未命中，从数据库中读取
		if (status == null) {
			CampaignEntity campaignEntity = flashSaleMapper.getCampaign(campaignId);
			log.info("campaign from database " + campaignEntity);
			if (campaignEntity == null) {
				throw new FlashSaleException(-102, "campaign does not exsit", itemId, campaignId, userId, "NoOrder");
			}
			
			// 1.3.如果状态不可用，返回
			if (campaignEntity.getStatus() == 0) {
				throw new FlashSaleException(-103, "campaign is unavailable", itemId, campaignId, userId, "NoOrder");
			}

			// 1.4.状态值写入缓存
			status = campaignEntity.getStatus();
			flashSaleRedisTemplate.opsForValue().set("status", status);
		}

		// 2.1.从缓存中读取商品库存
		Integer stock = flashSaleRedisTemplate.opsForValue().get(itemId);
		// Integer stock = flashSaleRedisTemplate.opsForValue().get(itemId);
		log.info("stock from cache======================================" + stock);

		// 2.2.缓存未命中，从数据库中读取
		if (stock == null) {
			ItemEntity itemEntity = flashSaleMapper.getItem(itemId);
			log.info("item from database " + itemEntity);
			if (itemEntity == null) {
				throw new FlashSaleException(-104, "item does not exsit", itemId, campaignId, userId, "NoOrder");
			}

			// 2.3.如果状态不可用，返回
			if (itemEntity.getStock() <= 0) {
				throw new FlashSaleException(-105, "stock is unavailable", itemId, campaignId, userId, "NoOrder");
			}

			// 2.4.库存值写入缓存
			stock = itemEntity.getStock();
			flashSaleRedisTemplate.opsForValue().set(itemId, stock);
		}
	}

	private void reduceStock(FlashSaleReq req) throws FlashSaleException {
		String itemId = req.getItemId();
		int campaignId = req.getCampaignId();
		String userId = req.getUserId();

		boolean success = false;
		try {
			success = flashSaleRedisTemplate.delete(itemId);
		} catch (Exception e) {
			// 扣减库存失败，返回
			log.error(e);
			throw new FlashSaleException(-106, "rpc:clear stock failed", itemId, campaignId, userId, "NoOrder", e);
		}

		// 并发时这里就有问题
		if (!success) {
			log.info("success========================" + success);
			//throw new FlashSaleException(-107, "clear stock failed", itemId, campaignId, userId, "NoOrder");
		}

		int rows = 0;
		try {
			// 匹配记录数量不等于1表示条件存在问题
			rows = flashSaleMapper.updateStock(itemId, 1);
			log.info("rows========================" + rows);
		} catch (Exception e) {
			// 扣减库存失败，返回
			log.error(e);
			throw new FlashSaleException(-108, "rpc:reduce stock failed", itemId, campaignId, userId, "NoOrder", e);
		}
		if (rows != 1) {
			throw new FlashSaleException(-109, "reduce stock failed", itemId, campaignId, userId, "NoOrder");
		}
	}

	private void checkout(FlashSaleReq req) throws FlashSaleException {
		String itemId = req.getItemId();
		int campaignId = req.getCampaignId();
		String userId = req.getUserId();

		CheckoutReq checkoutReq = new CheckoutReq(itemId, userId);
		CheckoutResp checkoutResp = null;
		try {
			checkoutResp = checkoutService.checkout(checkoutReq);
			log.info("========================" + checkoutReq);
			log.info("========================" + checkoutResp);
		} catch (Exception e) {
			log.error("checkout failed", e);
			throw new FlashSaleException(-110, "rpc:checkout failed", itemId, campaignId, userId, "NoOrder", e);
		}

		// TODO:恢复库存, 后面改异步重试
		if (checkoutResp.getCode() < 0) {
			// 这里存在无法恢复的风险
			// flashSaleRedisTemplate.delete(itemId);
			// flashSaleMapper.updateStock(itemId, -1);

			// 下单失败，返回
			throw new FlashSaleException(checkoutResp.getCode(), checkoutResp.getMessage(), itemId, campaignId, userId,
					checkoutResp.getOrderId());
		}
	}
}
