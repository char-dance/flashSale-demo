package com.next.demo.checkout;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.dubbo.config.annotation.Service;

import com.next.demo.checkout.CheckoutReq;
import com.next.demo.checkout.CheckoutResp;
import com.next.demo.checkout.CheckoutService;

@Service(version = "1.0.0", timeout = 3000)
public class CheckoutServiceImpl implements CheckoutService {
	private static final Log log = LogFactory.getLog(CheckoutServiceImpl.class);

	@Override
	public CheckoutResp checkout(CheckoutReq req) {
		log.info("========================" + req);

		int code = 0;
		String message = "checkout sucess";

		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			log.error("", e);
			code = -200;
			message = "checkout failed as sleep";
		}

		int i = 8;//(int) (System.nanoTime() % 9);
		if (i == 8) {
			code = -201;
			message = "checkout failed";
		}

		return new CheckoutResp(req.getItemId(), req.getUserId(), UUID.randomUUID().toString(), code, message);
	}

}
