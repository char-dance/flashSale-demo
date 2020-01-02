package com.bytecollege.demo.checkout;

public interface CheckoutService {

	default CheckoutResp checkout(CheckoutReq req) {
		return new CheckoutResp();
	}

}
