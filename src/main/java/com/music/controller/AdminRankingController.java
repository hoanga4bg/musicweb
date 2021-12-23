package com.music.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

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
import com.music.dto.RankingObject;
import com.music.entity.*;
import com.music.repository.RankingTableRepository;
import com.music.repository.SongRankRepository;
import com.music.repository.SongRepository;
@CrossOrigin("*")
@RestController
@RequestMapping(value="/api/ranking",produces = "application/json")
public class AdminRankingController {
	
	@Autowired
	private IRegionDAO regionDAO;
	@Autowired
	private IRankingDAO rankingDAO;
	@Autowired
	private RankingTableRepository rankRepo;
	@Autowired
	private SongRepository songRepo;
	@Autowired
	private SongRankRepository songRankRepo;
	
	@GetMapping
	public List<RankingObject> RankingHome(@RequestParam("id") String id,Model model) {
		Region region=regionDAO.findOneById(Long.parseLong(id));
		String startDate="2021-10-1";
		String endDate="2021-10-31";
		List<SongRank> listSongRank=new ArrayList<SongRank>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = null;
		try {
			sDate = sdf.parse(startDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date eDate = null;
		try {
			eDate = sdf.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<RankingObject> list=songRankRepo.getBestSongs(region.getId(),sDate,eDate);
		return list;
		
	}
	@PostMapping
	@Transactional
	public String create() {
		List<Region> list=regionDAO.findAll();
		for(Region r:list) {
			Date d=new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			Boolean check=false;
//			int month=calendar.get(Calendar.MONTH);
//			int year=calendar.get(Calendar.YEAR);
			int month=0;
			int year=2022;
			if(month>0) {
				check=rankingDAO.existMonthRank(month,year, r);
			}
			else {
				check=rankingDAO.existMonthRank(12,year-1, r);
			}
			System.out.println("created new table");
			if(check==false){
				if(month>0) {
					rankingDAO.createRegionRankingTable(r,month,year);
				}
				else {
					rankingDAO.createRegionRankingTable(r,12,year-1);
				}
			}
		}
		return "false";
	}
	@GetMapping("/get")
	public List<RankingTable> list(){
		return rankRepo.findAll();
	}
	@GetMapping("/drop")
	public boolean drop() {
		List<Region> list=regionDAO.findAll();
		for(Region r:list) {
			
			rankRepo.deleteAll(r.getListRankingTables());
		}
		return true;
		
	}
	@GetMapping("/drop/all")
	public boolean dropAll() {
		List<Region> list=regionDAO.findAll();

		songRankRepo.deleteAll();

		return true;
		
	}
	
}
