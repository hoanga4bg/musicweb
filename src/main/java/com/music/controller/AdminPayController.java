package com.music.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.music.business.pay.IPayDAO;
import com.music.entity.PayHistory;

@Controller
@RequestMapping("/admin/pay")
public class AdminPayController {
	
	@Autowired
	private IPayDAO payDAO;
	@GetMapping
	public String payHome(Model model) {
		Float total=0.0F;
		List<PayHistory> listPays=payDAO.findAll();
		System.out.println(listPays.size());
		for(PayHistory pay:listPays) {
			total+=pay.getFee();
		}
		model.addAttribute("listPays", listPays);
		model.addAttribute("total", total);
		return "admin/pay/payHome";
	}
	
	
	@GetMapping("/search")
	public String paySearch(Model model,@RequestParam("start") String sDate,@RequestParam("end") String eDate) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date startDate=sdf.parse(sDate);
		Date endDate=sdf.parse(eDate);
		List<PayHistory> listPays=payDAO.findByDateBetween(startDate, endDate);
		Float total=0.0F;
		for(PayHistory pay:listPays) {
			total+=pay.getFee();
		}
		model.addAttribute("listPays", listPays);
		model.addAttribute("total", total);
		return "admin/pay/payHome";
	}
	
}
