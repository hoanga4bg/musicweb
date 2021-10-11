package com.music.api;

import java.util.ArrayList;
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

import com.music.business.song.ISongDAO;

import com.music.entity.Song;
import com.music.repository.RankingTableRepository;

@RestController
@CrossOrigin("*")
public class MainApi {
	
	@Autowired
	private ISongDAO songDAO;
	@Autowired
	private RankingTableRepository rankRepo;
	
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
	@ResponseBody
	public List<Integer> getListYear(){
		return  rankRepo.listYear();
	}
	
	@RequestMapping(value = "/api/month",method =RequestMethod.GET)
	@ResponseBody
	public List<Integer> getListYear(@RequestParam("y") String year){
		return  rankRepo.listMonth(Integer.parseInt(year));
	}
}
