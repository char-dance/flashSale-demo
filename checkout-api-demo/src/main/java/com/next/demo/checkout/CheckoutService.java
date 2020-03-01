package com.next.demo.checkout;

public interface CheckoutService {

	default CheckoutResp checkout(CheckoutReq req) {
		return new CheckoutResp();
	}

}
