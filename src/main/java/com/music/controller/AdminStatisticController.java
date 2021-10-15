package com.music.controller;



import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;






@Controller
public class AdminStatisticController {
	
	
	@RequestMapping(value="/admin/statistic",method = RequestMethod.GET)
	public String statisticHome(){
		return "admin/statistic/statistic";
	}
	
	
}
