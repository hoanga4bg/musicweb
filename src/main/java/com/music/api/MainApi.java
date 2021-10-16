package com.music.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.music.apiori.AssociationRule;
import com.music.apiori.ItemSet;
import com.music.apiori.ItemSetCollection;
import com.music.business.account.IAccountDAO;
import com.music.business.listens.IListenDAO;
import com.music.business.region.IRegionDAO;
import com.music.business.song.ISongDAO;
import com.music.entity.Account;
import com.music.entity.PlayList;
import com.music.entity.Region;
import com.music.entity.Rule;
import com.music.entity.Song;
import com.music.entity.SongInPlayList;
import com.music.repository.RankingTableRepository;
import com.music.repository.RuleRepository;

@RestController
@CrossOrigin("*")
public class MainApi {
	
	@Autowired
	private ISongDAO songDAO;
	@Autowired
	private RankingTableRepository rankRepo;
	@Autowired
	private IListenDAO listenDAO;
	@Autowired
	private RuleRepository ruleRepository;
	@Autowired
	private IRegionDAO regionDAO;
	@Autowired
	private IAccountDAO accountDAO;
	@Autowired
	private RuleRepository ruleRepo;
	@RequestMapping(value = "/api/songAutoComplete",method = RequestMethod.GET)
	public List<String> songAutoComplete(@RequestParam(value = "term" , required = false, defaultValue = "") String term){
		List<Song> listSongs=songDAO.findBySongNameContain(term);
		List<String> listNames=new ArrayList<String>();
		for(Song s:listSongs) {
			listNames.add(s.getName());
		}
		return listNames;
	}
	
	@RequestMapping(value = "/api/year",method =RequestMethod.GET)
	public List<Integer> getListYear(){
		return  rankRepo.listYear();
	}
	
	@RequestMapping(value = "/api/month",method =RequestMethod.GET)
	public List<Integer> getListYear(@RequestParam("y") String year){
		return  rankRepo.listMonth(Integer.parseInt(year));
	}
	
	@RequestMapping(value = "/api/getrule",method = RequestMethod.GET)
	public int rule(){
		List<Rule> rules=ruleRepository.findAll();
		return rules.size();
	}
	
	
	@RequestMapping(value="/api/statistic/region",produces = "application/json",method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> statisticMonth(){
		List<Result> result=new ArrayList<Result>();
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month=calendar.get(Calendar.MONTH)+1;
		int year=calendar.get(Calendar.YEAR);
		
		
		List<Region> regions=regionDAO.findAll();
		for(Region r:regions) {
			result.add(new Result(r.getName(), listenDAO.statisticByRegionId(r.getId(), month, year)));
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	
	@RequestMapping(value="/api/statistic",produces = "application/json",method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> statisticRegion(){
		List<Result> result=new ArrayList<Result>();
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month=calendar.get(Calendar.MONTH)+1;
		int year=calendar.get(Calendar.YEAR);
		
		result.add(new Result(month+"-"+year, listenDAO.statisticByMonthYear(month, year)));
		for(int i=1;i<5;i++) {
			if((month-i)>0) {
				result.add(new Result((month-i)+"-"+year, listenDAO.statisticByMonthYear((month-i), year)));
			}
			else {
				result.add(new Result((12+month-i)+"-"+(year-1), listenDAO.statisticByMonthYear((12+month-i), (year-1))));
			}
		}
		Collections.reverse(result);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@RequestMapping(value="/api/apiori",produces = "application/json",method = RequestMethod.GET)
	@ResponseBody
	public List<Rule> apiori(){
		ruleRepo.deleteAll();
		List<Account> list=accountDAO.findAll();
		ItemSetCollection itemCol=new ItemSetCollection();
		for(Account a:list) {
			if(a.getListPlayLists().size()>0) {
				for(PlayList play:a.getListPlayLists()) {
					if(play.getSongInPlayLists().size()>0) {
						List<SongInPlayList> sipList= play.getSongInPlayLists();
						ItemSet item=new ItemSet();
						for(SongInPlayList sip:sipList) {					
							item.add(sip.getSong().getId()+"");
						}
						itemCol.add(item);
					}
				}
			}
		}
		
		
		AssociationRule as = new AssociationRule();
		List<AssociationRule> listAss = new ArrayList<AssociationRule>();
		System.out.println(as.FindingLargeItemset(itemCol, 0.15));
		listAss = as.assRule(itemCol, as.FindingLargeItemset(itemCol, 0.15), 20);
		List<Rule> rules=new ArrayList<>();
		for(AssociationRule ass:listAss) {
			String x="";
			for(String a:ass.getX()) {
				x+=(a+",");
			}
			String y="";
			for(String a:ass.getY()) {
				y+=(a+",");
			}
			Rule temp=new Rule();
			temp.setX(x);
			temp.setY(y);
			rules.add(temp);
		}
		ruleRepo.saveAll(rules);
		return rules;
	}
	
	
	
	
	public class Result{
		String xCol;
		Long totalListen;
		public Result(String xCol, Long totalListen) {
			super();
			this.xCol = xCol;
			this.totalListen = totalListen;
		}
		public String getXCol() {
			return xCol;
		}
		public void setXCol(String xCol) {
			this.xCol = xCol;
		}
		public Long getTotalListen() {
			return totalListen;
		}
		public void setTotalListen(Long totalListen) {
			this.totalListen = totalListen;
		}
		
		
	}
}
