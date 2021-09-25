package com.music.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mservice.allinone.models.CaptureMoMoResponse;
import com.mservice.allinone.models.PayATMResponse;
import com.mservice.allinone.models.QueryStatusTransactionResponse;
import com.mservice.allinone.processor.allinone.CaptureMoMo;
import com.mservice.allinone.processor.allinone.PayATM;
import com.mservice.allinone.processor.allinone.QueryStatusTransaction;
import com.mservice.shared.sharedmodels.Environment;
import com.music.config.MoMoService;

@Controller
@RequestMapping("/payment")
public class PaymentController {
	@Autowired
	private MoMoService momoService;
	@GetMapping
	private String paymentHome() {
		return "payment";
	}
	
	@PostMapping("/momo")
	private String momopayment() {
        String payUrl=momoService.getMoMoPayUrl();
        return payUrl == null ? "redirect:/payment" : payUrl;
	}
}
