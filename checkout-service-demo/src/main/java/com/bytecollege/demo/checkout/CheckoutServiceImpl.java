package com.bytecollege.demo.checkout;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "1.0.0", timeout = 3000)
public class CheckoutServiceImpl implements CheckoutService {
	private static final Log log = LogFactory.getLog(CheckoutServiceImpl.class);

	@Override
	public CheckoutResp checkout(CheckoutReq req) {
		log.info("========================" + req);

		long id = req.getId();
		String itemId = req.getItemId();
		String orderId = UUID.randomUUID().toString();
		boolean success = id % 9 == 0 ? false : true;

		CheckoutResp resp = new CheckoutResp(id, itemId, orderId, success);
		log.info("========================" + resp);

		return resp;
	}

}
