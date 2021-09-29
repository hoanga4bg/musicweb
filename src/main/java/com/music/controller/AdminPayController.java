package com.music.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/pay")
public class AdminPayController {
	public String payHome() {
		
		return "admin/pay/payHome";
	}
}
