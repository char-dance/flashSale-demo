package com.bytecollege.demo.flashSale;

import java.util.concurrent.atomic.AtomicInteger;

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

	private final AtomicInteger seq = new AtomicInteger();

	@Reference(version = "1.0.0", timeout = 3000)
	private FlashSaleService flashSaleService;

	// http://localhost:8080/flashSale/check?itemId=1A2B3C4D5E6F&campaignId=1&userId=ruanwei
	@GetMapping("/check")
	public FlashSaleModel check(FlashSaleCommand command) {
		log.info("========================" + command);

		FlashSaleReq req = new FlashSaleReq(command.getItemId(), command.getCampaignId(), command.getUserId());
		FlashSaleResp resp = flashSaleService.check(req);
		log.info("========================" + req);
		log.info("========================" + resp);

		FlashSaleModel model = new FlashSaleModel(resp.getItemId(), resp.getUserId(), resp.getOrderId(), resp.getCode(),
				resp.getMessage());
		log.info("========================" + model);
		return model;
	}

	// http://localhost:8080/flashSale/flash?itemId=1A2B3C4D5E6F&campaignId=1&userId=ruanwei
	@GetMapping("/flash")
	public FlashSaleModel flash(FlashSaleCommand command) {
		log.info("========================" + command);
		int seqId = seq.incrementAndGet();

		FlashSaleReq req = new FlashSaleReq(command.getItemId(), command.getCampaignId(), command.getUserId());
		FlashSaleResp resp = flashSaleService.flash(req);
		log.info("========================" + req);
		log.info("========================" + resp);

		FlashSaleModel model = new FlashSaleModel(resp.getItemId(), resp.getUserId(), resp.getOrderId(), resp.getCode(),
				resp.getMessage());
		log.info("========================" + model);
		return model;
	}
}
