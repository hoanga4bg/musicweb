package com.music.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.music.business.song.ISongDAO;
import com.music.entity.SingSong;
import com.music.entity.Singer;
import com.music.entity.Song;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(produces = "application/json")
public class SearchApi {
	@Autowired
	private ISongDAO songDAO;
	
	@RequestMapping(value = "/songAutoComplete",method =RequestMethod.GET)
	@ResponseBody
	public List<String> songAutoComplete(@RequestParam(value = "term" , required = false, defaultValue = "") String term){
		List<Song> listSongs=songDAO.findBySongNameContain(term);
		List<String> listNames=new ArrayList<String>();
		//Lấy danh sách tên ca sĩ
		for(int i=0;i<listSongs.size();i++) {
			String temp="";
			List<SingSong> listSingSong=listSongs.get(i).getListSingSong();
			//Thêm tên vào danh sách
			for(int j=0;j<listSingSong.size()-1;j++) {
				
				temp+=(listSingSong.get(i).getSinger().getName()+", ");
			}
			//Tên cuối
			temp+=(listSingSong.get(listSingSong.size()-1).getSinger().getName());
		
			listNames.add(listSongs.get(i).getName() + " - " +temp);
		}
		return listNames;
	}
}
