package com.bytecollege.demo.flashSale;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flashSale")
public class FalshSaleController {
	private static final Log log = LogFactory.getLog(FalshSaleController.class);

	private final AtomicLong counter = new AtomicLong();

	@Reference(version = "1.0.0", timeout = 3000)
	private FlashSaleService flashSaleService;

	// http://localhost:8080/flashSale?itemId=ABCDEF
	@GetMapping("/checkout")
	public CheckoutModel checkout(@RequestParam(value = "itemId", defaultValue = "ABC123") String itemId) {
		log.info("========================" + itemId);
		long id = counter.incrementAndGet();
		FlashSaleReq flashSaleReq = new FlashSaleReq(id, itemId);
		
		FlashSaleResp flashSaleResp = flashSaleService.checkout(flashSaleReq);
		String resultMsg = "checkout " + (flashSaleResp.isSuccess() ? "succeed" : "failed");
		log.info("========================" + flashSaleResp);

		return new CheckoutModel(flashSaleResp.getId(), flashSaleResp.getItemId(), resultMsg);
	}
}
