package com.bytecollege.demo.flashSale;

public interface FlashSaleService {

	default String greet(String name) {
		return "Good Goog morning, " + name;
	}

	default FlashSaleResp checkout(FlashSaleReq req) {
		return new FlashSaleResp();
	}

}
