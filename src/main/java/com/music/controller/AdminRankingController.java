package com.music.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.music.business.ranking.IRankingDAO;
import com.music.business.region.IRegionDAO;
import com.music.business.region.RegionDAO;
import com.music.entity.*;
@CrossOrigin("*")
@RestController
@RequestMapping("/admin/ranking")
public class AdminRankingController {
	
	@Autowired
	private IRegionDAO regionDAO;
	@Autowired
	private IRankingDAO rankingDAO;
	
	@GetMapping
	public String RankingHome(@RequestParam("id") String id,
							@RequestParam("month") String month,
							@RequestParam("year") String year,
							Model model) {
		Region region=new Region();
		List<Region> list=regionDAO.findAll();
		RankingTable rankTable=new RankingTable();
		if(id==null||id.equals("")){
			region=list.get(0);
			if(region.getListRankingTables().size()>0) {
				rankTable=region.getListRankingTables().get(region.getListRankingTables().size()-1);
				month=rankTable.getMonth()+"";
				year=rankTable.getYear()+"";
			}
		}
		else {
			if(month.equals("")||year.equals("")) {
				region=regionDAO.findOneById(Long.parseLong(id));
				rankTable=region.getListRankingTables().get(region.getListRankingTables().size()-1);
				month=rankTable.getMonth()+"";
				year=rankTable.getYear()+"";
			}
			else if(month.equals("")==false&&year.equals("")==false){
				region=regionDAO.findOneById(Long.parseLong(id));
				rankTable=rankingDAO.getRankByRegionAndTime(region, Integer.parseInt(month),Integer.parseInt(year));
			}
			
			
		}
		boolean newest=true;
		for(Region r:list) {
			Date d=new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			Boolean check=rankingDAO.existMonthRank(calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR), r);
			if(check==false) {
				newest=false;
			}
		}

		model.addAttribute("newest", newest);
		model.addAttribute("rankTable", rankTable.getListSongRanks());
		model.addAttribute("region",region);
		model.addAttribute("listRegions", list);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		return "admin/ranking/ranking";
	}
	@PostMapping
	public String create() {
		List<Region> list=regionDAO.findAll();
		for(Region r:list) {
			Date d=new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			Boolean check=rankingDAO.existMonthRank(calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.YEAR), r);
			if(check==false){
				rankingDAO.createRegionRankingTable(r,calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.YEAR));
			}
		}
		return "redirect:/admin/ranking?id=&month=&year=";
	}
}
