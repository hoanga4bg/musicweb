package com.music.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.music.business.ranking.IRankingDAO;
import com.music.business.ranking.RankingDAO;
import com.music.business.region.IRegionDAO;
import com.music.entity.RankingTable;
import com.music.entity.Region;
import com.music.entity.SongRank;

@Controller
@RequestMapping("/rank")
public class RankingController {
	
	@Autowired
	private IRankingDAO rankingDAO;
	
	@Autowired
	private IRegionDAO regionDAO;
	@GetMapping
	public String ranking(@RequestParam("region") String regionId,
			@RequestParam("month") String month,
			@RequestParam("year") String year, Model model) {
		List<Region> listRegions=regionDAO.findAll();
		Region region=null;
		RankingTable rankTable=null;
		//Nếu không xác định được region thì chọn region đầu tiên
		if(regionId==null||regionId.equals("")) {
			region=listRegions.get(0);
			
		}
		else {
			region=regionDAO.findOneById(Long.parseLong(regionId));
		}
		
		int viewMonth=0;
		int viewYear=0;
		if(month==null||year==null||month.equals("")||year.equals("")) {
			//Lấy bảng xếp hạng mới nhất
			rankTable=region.getListRankingTables().get(region.getListRankingTables().size()-1);
			viewMonth=region.getListRankingTables().get(region.getListRankingTables().size()-1).getMonth();
			viewYear=region.getListRankingTables().get(region.getListRankingTables().size()-1).getYear();
		}
		else {
			rankTable=rankingDAO.getRankByRegionAndTime(region, Integer.parseInt(month), Integer.parseInt(year));
			viewMonth=Integer.parseInt(month);
			viewYear=Integer.parseInt(year);
		}
		
		List<SongRank> listSongRanks=rankTable.getListSongRanks();
		Collections.sort(listSongRanks,new Comparator<SongRank>() {
			@Override
			public int compare(SongRank o1, SongRank o2) {
				return o1.getRankNumber()-o2.getRankNumber();
				
			}
		});
		
		model.addAttribute("listRegions", listRegions);
		model.addAttribute("region", region);
		model.addAttribute("month", viewMonth );
		model.addAttribute("year",viewYear);
		model.addAttribute("rankingTable", listSongRanks);
		return "web/rank/rank";

	}
	

}
