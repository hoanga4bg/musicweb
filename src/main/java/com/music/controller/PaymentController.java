package com.music.controller;

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
import com.music.business.account.IAccountDAO;
import com.music.config.MoMoService;
import com.music.entity.Account;

@Controller
@RequestMapping("/payment")
public class PaymentController {
	@Autowired
	private MoMoService momoService;
	
	@Autowired
	private IAccountDAO accountDAO;
	@GetMapping
	private String paymentHome() {
		return "web/payment";
	}
	
	@PostMapping("/momo")
	private String momopayment() {
		Account account=accountDAO.getLogingAccount();
        String payUrl=momoService.getMoMoPayUrl(account.getId()+"");
        return payUrl == null ? "redirect:/payment" : payUrl;
	}
}
