package com.bytecollege.demo.checkout;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.dubbo.config.annotation.Service;

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
			code = -11;
			message = "checkout failed as sleep";
		}

		int i = (int) (System.nanoTime() % 9);
		if (i == 8) {
			code = -10;
			message = "checkout falied";
		}

		return new CheckoutResp(req.getItemId(), req.getUserId(), UUID.randomUUID().toString(), code, message);
	}

}
