package com.bytecollege.demo.flashSale;

public interface FlashSaleService {

	default FlashSaleResp flash(FlashSaleReq req) {
		return new FlashSaleResp();
	}

}
