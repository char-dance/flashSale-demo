package com.bytecollege.demo.flashSale;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flashSale")
public class FalshSaleController {
	private static final Log log = LogFactory.getLog(FalshSaleController.class);

	private final AtomicLong counter = new AtomicLong();

	@Reference(version = "1.0.0", timeout = 3000)
	private FlashSaleService flashSaleService;

	// http://localhost:8080/flashSale/checkout?itemId=ABCDEF&userId=ruanwei
	@GetMapping("/checkout")
	public CheckoutModel checkout(CheckoutCommand command) {
		log.info("========================" + command);
		long id = counter.incrementAndGet();
		FlashSaleReq req = new FlashSaleReq(id, command.getItemId());
		log.info("========================" + req);

		FlashSaleResp resp = flashSaleService.checkout(req);
		String resultMsg = "checkout " + (resp.isSuccess() ? "succeed" : "failed");
		log.info("========================" + resp);

		CheckoutModel model = new CheckoutModel(resp.getId(), resp.getItemId(), resultMsg);
		log.info("========================" + model);
		return model;
	}
}
