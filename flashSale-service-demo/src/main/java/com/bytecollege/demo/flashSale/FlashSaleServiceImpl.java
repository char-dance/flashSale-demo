package com.bytecollege.demo.flashSale;

import java.time.LocalDateTime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.bytecollege.demo.checkout.CheckoutReq;
import com.bytecollege.demo.checkout.CheckoutResp;
import com.bytecollege.demo.checkout.CheckoutService;
import com.bytecollege.demo.flashSale.dao.GreetingEntity;
import com.bytecollege.demo.flashSale.dao.GreetingMapper;

@Service(version = "1.0.0", timeout = 3000)
public class FlashSaleServiceImpl implements FlashSaleService {
	private static final Log log = LogFactory.getLog(FlashSaleServiceImpl.class);

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private GreetingMapper greetingMapper;

	@Reference(version = "1.0.0", timeout = 3000)
	private CheckoutService checkoutService;

	@Override
	public FlashSaleResp checkout(FlashSaleReq req) {
		log.info("========================" + req);

		long id = req.getId();
		String itemId = req.getItemId();
		LocalDateTime requestTime = req.getRequestTime();

		redisTemplate.opsForValue().set("foo", "bar");
		String value = redisTemplate.opsForValue().get("foo");
		System.out.println("value from redis======================================" + value);

		GreetingEntity greetingEntity = greetingMapper.getContentById(1);
		String content = greetingEntity.getContent();
		System.out.println("value from mysql======================================" + content);

		CheckoutReq checkoutReq = new CheckoutReq(id, itemId);
		CheckoutResp checkoutResp = checkoutService.checkout(checkoutReq);

		FlashSaleResp resp = new FlashSaleResp(id, itemId, checkoutResp.getOrderId(), checkoutResp.isSuccess(),
				checkoutResp.getCheckoutTime());
		log.info("========================" + resp);
		
		return resp;
	}

}
