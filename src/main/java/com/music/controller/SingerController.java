package com.music.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.music.business.singer.ISingerDAO;
import com.music.dto.SongDTO;
import com.music.dto.convert.SongConvert;
import com.music.entity.SingSong;
import com.music.entity.Singer;
import com.music.entity.Song;


@Controller
@RequestMapping("/singer")
public class SingerController {
	
	
	@Autowired
	private ISingerDAO singerDAO;
	@Autowired
	private SongConvert songConvert;
	@GetMapping("/all")
	public String singerHome(Model model) {
		List<Singer> listSingers=singerDAO.findAll();
		Collections.reverse(listSingers);
		model.addAttribute("listSingers",listSingers);
		return "web/singer/allSinger";
	}
	
	
	@GetMapping
	public String singerSong(Model model,@RequestParam("id") String id) {
		Singer singer=singerDAO.findOneById(Long.parseLong(id));
		List<SongDTO> listSongs=new ArrayList<SongDTO>();
		for(SingSong s:singer.getListSingSong()) {
			SongDTO song=songConvert.toDTO(s.getSong());
			listSongs.add(song);
		}
		Collections.reverse(listSongs);
		model.addAttribute("singer", singer);
		model.addAttribute("listSongs",listSongs);
		return "web/singer/singer";
	}
	
	
}
