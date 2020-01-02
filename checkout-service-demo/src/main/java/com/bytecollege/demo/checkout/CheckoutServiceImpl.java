package com.bytecollege.demo.checkout;

import org.apache.dubbo.config.annotation.Service;

@Service(version = "1.0.0", timeout = 3000)
public class CheckoutServiceImpl implements CheckoutService {

	@Override
	public CheckoutResp checkout(CheckoutReq req) {
		return new CheckoutResp();
	}

}
