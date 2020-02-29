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

	@Autowired
	private RedisTemplate<String, Integer> flashSaleRedisTemplate;

	@Autowired
	private FlashSaleMapper flashSaleMapper;

	@Reference(version = "1.0.0", timeout = 3000)
	private CheckoutService checkoutService;

	private static final Map<String, Integer> STOCK_CACHE = new HashMap<>();

	@Override
	public FlashSaleResp flash(FlashSaleReq req) {
		log.info("========================" + req);

		// 1.检查库存
		checkStock(req.getItemId());

		// 2.下单
		CheckoutReq checkoutReq = new CheckoutReq(req.getItemId(), req.getUserId(), req.getSeqId());
		CheckoutResp checkoutResp = checkoutService.checkout(checkoutReq);

		// 3.扣减库存
		stringRedisTemplate.opsForValue().set("foo", "bar");
		String value = stringRedisTemplate.opsForValue().get("foo");
		System.out.println("value from redis======================================" + value);

		// 返回响应
		FlashSaleResp resp = new FlashSaleResp(checkoutResp.getItemId(), checkoutResp.getUserId(),
				checkoutResp.getOrderId(), checkoutResp.isSuccess(), checkoutResp.getSeqId());
		log.info("========================" + resp);
		return resp;
	}

	private int checkStock(String itemId) {
		// String stock = stringRedisTemplate.opsForValue().get(itemId);
		Integer stock = flashSaleRedisTemplate.opsForValue().get(itemId);
		if (stock == null) {
			ItemEntity itemEntity = flashSaleMapper.getItem(itemId);
			stock = itemEntity.getStock();
			System.out.println("stock from mysql======================================" + stock);
		}
		return stock;
	}

}
