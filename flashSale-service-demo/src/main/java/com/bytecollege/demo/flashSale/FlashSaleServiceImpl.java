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
	public FlashSaleResp flash(FlashSaleReq req) {
		log.info("========================" + req);

		String itemId = req.getItemId();
		String userId = req.getUserId();

		// 1.检查库存
		int stock = checkStock(itemId);
		if (stock <= 0) {
			return new FlashSaleResp(itemId, userId, "NoOrder", -1, "stock is unavailable");
		}

		// 2.下单
		CheckoutReq checkoutReq = new CheckoutReq(itemId, userId);
		CheckoutResp checkoutResp = checkoutService.checkout(checkoutReq);
		log.info("========================" + checkoutReq);
		log.info("========================" + checkoutResp);

		if (checkoutResp.getCode() < 0) {
			return new FlashSaleResp(itemId, userId, checkoutResp.getOrderId(), -2, checkoutResp.getMessage());
		}

		// 3.扣减库存
		stringRedisTemplate.delete(itemId);
		flashSaleMapper.updateStock(itemId, 1);

		// 返回响应
		return new FlashSaleResp(itemId, userId, checkoutResp.getOrderId(), checkoutResp.getCode(),
				"flash sale success");
	}

	private int checkStock(String itemId) {
		String stock = stringRedisTemplate.opsForValue().get(itemId);
		// Integer stock = flashSaleRedisTemplate.opsForValue().get(itemId);
		log.info("stock from redis======================================" + stock);
		if (stock == null) {
			ItemEntity itemEntity = flashSaleMapper.getItem(itemId);
			stock = itemEntity.getStock() + "";

			log.info("stock from mysql======================================" + stock);
			stringRedisTemplate.opsForValue().set(itemId, stock);
		}

		return Integer.parseInt(stock);
	}

}
