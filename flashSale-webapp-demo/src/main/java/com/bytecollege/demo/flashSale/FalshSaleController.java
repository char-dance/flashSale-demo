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

	// http://localhost:8080/flashSale/flash?itemId=1A2B3C4D5E6F&userId=ruanwei
	@GetMapping("/flash")
	public FlashSaleModel flash(FlashSaleCommand command) {
		log.info("========================" + command);

		int seqId = seq.incrementAndGet();
		FlashSaleReq req = new FlashSaleReq(command.getItemId(), command.getUserId(), seqId);

		FlashSaleResp resp = flashSaleService.flash(req);
		log.info("========================" + resp);

		String resultMsg = "flash " + (resp.isSuccess() ? "succeed" : "failed");
		FlashSaleModel model = new FlashSaleModel(resp.getItemId(), resp.getUserId(), resultMsg, resp.getSeqId());
		log.info("========================" + model);
		return model;
	}
}
