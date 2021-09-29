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
import com.music.business.notification.INotificationDAO;
import com.music.entity.Account;
import com.music.entity.Notification;
//Controller thong bao
@Controller
@RequestMapping("/admin/noti")
public class AdminNotiController {
	
	@Autowired
	private INotificationDAO notiDAO;
	
	@Autowired
	private IAccountDAO accountDAO;
	@GetMapping
	public String notiHome(Model model) {
		List<Notification> listNotis=new ArrayList<Notification>(); 
		listNotis=notiDAO.findAllByAccount(accountDAO.findByUsername("admin"));
		Collections.reverse(listNotis);
		model.addAttribute("listNotis", listNotis);
		return "admin/noti/notiHome";
	}
	@GetMapping("/uncheck")
	public String unCheckNoti(Model model) {
		List<Notification> listNotis=new ArrayList<Notification>(); 
		listNotis=notiDAO.findAllUnCheckNoti(accountDAO.findByUsername("admin"));
		Collections.reverse(listNotis);
		model.addAttribute("listNotis", listNotis);
		return "admin/noti/notiHome";
	}
	
	@PostMapping("/check")
	public String updateNoti(@RequestParam("id") String id) {
		Notification noti=notiDAO.findById(Long.parseLong(id));
		if(noti.getChecked()==true) {
			noti.setChecked(false);
			noti.setCheckDate(null);
		}
		else {
			noti.setChecked(true);
			noti.setCheckDate(new Date());
		}
		notiDAO.save(noti);
		return "redirect:/admin/noti";
	}
	
}
