package com.bytecollege.demo.flashSale;

public interface FlashSaleService {

	default FlashSaleResp checkout(FlashSaleReq req) {
		return new FlashSaleResp();
	}

}
