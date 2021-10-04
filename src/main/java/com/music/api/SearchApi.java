package com.music.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.music.apiori.AssociationRule;
import com.music.apiori.ItemSet;
import com.music.apiori.ItemSetCollection;
import com.music.business.account.IAccountDAO;
import com.music.business.song.ISongDAO;
import com.music.dto.RankingObject;
import com.music.entity.Account;
import com.music.entity.PlayList;
import com.music.entity.Rule;
import com.music.entity.SingSong;
import com.music.entity.Singer;
import com.music.entity.Song;
import com.music.entity.SongInPlayList;
import com.music.entity.SongRank;
import com.music.repository.RankingTableRepository;
import com.music.repository.RuleRepository;
import com.music.repository.SongRankRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(produces = "application/json")
public class SearchApi {
	@Autowired
	private ISongDAO songDAO;
	@Autowired
	private IAccountDAO accountDAO;
	
	@Autowired
	private SongRankRepository songRankRepo;
	@Autowired
	private RuleRepository ruleRepo;
	@Autowired
	private RankingTableRepository rankRepo;
	@RequestMapping(value = "/api/getAccount",method =RequestMethod.GET)
	@ResponseBody
	public List<Account> accounts(){
		return accountDAO.findAll();
	}
	@RequestMapping(value = "/api/rank",method =RequestMethod.GET)
	@ResponseBody
	public List<RankingObject> getSongRank(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = null;
		try {
			sDate = sdf.parse("2020-10-1");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date eDate = null;
		try {
			eDate = sdf.parse("2022-9-3");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  songRankRepo.getBestSongs(1L,sDate ,eDate );
	}
	
	@RequestMapping(value = "/api/year",method =RequestMethod.GET)
	@ResponseBody
	public List<Integer> getListYear(){
		return  rankRepo.listYear();
	}
	
	@RequestMapping(value = "/api/month",method =RequestMethod.GET)
	@ResponseBody
	public List<Integer> getListYear(@RequestParam("y") String year){
		return  rankRepo.listMonth(Integer.parseInt(year));
	}
	

	@Scheduled(cron = "0 54 18 ? * SUN")
	public List<Rule> apiori(){
		ruleRepo.deleteAll();
		List<Account> list=accountDAO.findAll();
		System.out.print(list.size());
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
		listAss = as.assRule(itemCol, as.FindingLargeItemset(itemCol, 0.15), 10);
		List<Rule> rules=new ArrayList<>();
		for(AssociationRule ass:listAss) {
			String x="";
			for(String a:ass.getX()) {
				x+=a+",";
			}
			String y="";
			for(String a:ass.getY()) {
				y+=a+",";
			}
			Rule temp=new Rule();
			temp.setX(x);
			temp.setY(y);
			rules.add(temp);
		}
		ruleRepo.saveAll(rules);
		return rules;
	}
	
	
}
