package com.music.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.music.entity.Account;
import com.music.entity.PlayList;
import com.music.entity.SingSong;
import com.music.entity.Singer;
import com.music.entity.Song;
import com.music.entity.SongInPlayList;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(produces = "application/json")
public class SearchApi {
	@Autowired
	private ISongDAO songDAO;
	@Autowired
	private IAccountDAO accountDAO;
	@RequestMapping(value = "/api/getAccount",method =RequestMethod.GET)
	@ResponseBody
	public List<Account> accounts(){
		return accountDAO.findAll();
	}
	
	@RequestMapping(value = "/api/apiori",method =RequestMethod.GET)
	@ResponseBody	
	public List<AssociationRule> apiori(){
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
		listAss = as.assRule(itemCol, as.FindingLargeItemset(itemCol, 0.15), 0);
		
		return listAss;
	}
}
