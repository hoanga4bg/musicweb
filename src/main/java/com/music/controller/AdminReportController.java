package com.music.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.music.business.account.IAccountDAO;
import com.music.business.report.IReportDAO;
import com.music.entity.Account;
import com.music.entity.Report;

@Controller
@RequestMapping("/admin/report")
public class AdminReportController {
	
	@Autowired
	private IReportDAO reportDAO;
	

	@GetMapping
	public String reportHome(Model model) {
		List<Report> listReports=new ArrayList<Report>(); 
		listReports=reportDAO.findAll();
		Collections.reverse(listReports);
		model.addAttribute("listReports", listReports);
		return "admin/report/report";
	}
	@GetMapping("/uncheck")
	public String unCheckNoti(Model model) {
		List<Report> listReports=new ArrayList<Report>(); 
		listReports=reportDAO.findAllUnCheckReport();
		Collections.reverse(listReports);
		model.addAttribute("listReports", listReports);
		return "admin/report/report";
	}
	
	@PostMapping("/check")
	public String updateReport(@RequestParam("id") String id) {
		Report noti=reportDAO.findById(Long.parseLong(id));
		if(noti.getChecked()==true) {
			noti.setChecked(false);
			noti.setCheckDate(null);
		}
		else {
			noti.setChecked(true);
			noti.setCheckDate(new Date());
		}
		reportDAO.save(noti);
		return "redirect:/admin/report";
	}
	
}
