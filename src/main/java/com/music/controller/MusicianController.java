package com.music.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.music.business.musician.IMusicianDAO;
import com.music.business.singer.ISingerDAO;
import com.music.dto.SongDTO;
import com.music.dto.convert.SongConvert;
import com.music.entity.Musician;

import com.music.entity.Song;
@Controller
@RequestMapping("/musician")
public class MusicianController {
	@Autowired
	private IMusicianDAO musicianDAO;
	@Autowired
	private SongConvert songConvert;
	@GetMapping("/all")
	public String musicianHome(Model model) {
		List<Musician> listMusicians=musicianDAO.findAll();
		Collections.reverse(listMusicians);
		model.addAttribute("listMusicians",listMusicians);
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
}

