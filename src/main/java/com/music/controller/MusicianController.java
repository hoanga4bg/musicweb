package com.music.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.music.business.musician.IMusicianDAO;
import com.music.business.singer.ISingerDAO;
import com.music.dto.SongDTO;
import com.music.dto.convert.SongConvert;
import com.music.entity.Musician;
import com.music.entity.Singer;
import com.music.entity.Song;
@Controller
@RequestMapping("/musician")
public class MusicianController {
	@Autowired
	private IMusicianDAO musicianDAO;
	@Autowired
	private SongConvert songConvert;
	@GetMapping("/all")
	public String musicianHome(@RequestParam("page") String page,Model model) {
		int currentPage=0;
		int limit =10;
		try {
			currentPage=Integer.parseInt(page);
		}
		catch(Exception e){
			currentPage=0;
		}
		Pageable pageable=PageRequest.of(currentPage-1, limit);
		List<Musician> listMusicians=musicianDAO.findAll(pageable);
		int totalPage=((int) Math.ceil((musicianDAO.totalItem()*1.0/limit)));
		
		model.addAttribute("listMusicians",listMusicians);
		model.addAttribute("page",currentPage);
		model.addAttribute("totalPage", totalPage);
		return "web/musician/allMusician";
	}
	
	
	@GetMapping
	public String musicianSong(Model model,@RequestParam("id") String id) {
		Musician musician=musicianDAO.findOneById(Long.parseLong(id));
		List<SongDTO> listSongs=new ArrayList<SongDTO>();
		for(Song s:musician.getListSong()) {
			SongDTO song=songConvert.toDTO(s);
			listSongs.add(song);
		}
		Collections.reverse(listSongs);
		model.addAttribute("musician", musician);
		model.addAttribute("listSongs",listSongs);
		return "web/musician/musician";
	}
	@GetMapping("/autoComplete")
	@ResponseBody
	public List<String> musicianName(@RequestParam(value = "term" , required = false, defaultValue = "") String term) {
		List<Musician> list=musicianDAO.findByNameContain(term);
		List<String> names=new ArrayList<String>();
		for(Musician s:list) {
			names.add(s.getName());
		}
		return names;
	}
	
	@GetMapping("/search")
	public String search(@RequestParam("musician") String musician,Model model) {
		List<Musician> listMusicians=new ArrayList<Musician>();
		listMusicians=musicianDAO.findByNameContain(musician);
		model.addAttribute("listMusicians", listMusicians);
		return "web/musician/allMusician";
		
	}
}

