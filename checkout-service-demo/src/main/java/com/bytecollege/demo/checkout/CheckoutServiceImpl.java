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

		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			log.error("", e);
		} finally {

		}

		int seqId = req.getSeqId();
		boolean success = seqId % 9 == 0 ? false : true;
		CheckoutResp resp = new CheckoutResp(req.getItemId(), req.getUserId(), UUID.randomUUID().toString(), success,
				req.getSeqId());
		log.info("========================" + resp);

		return resp;

	}

}
