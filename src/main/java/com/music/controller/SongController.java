package com.music.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.music.business.category.ICategoryDAO;
import com.music.business.listens.IListenDAO;

import com.music.business.region.IRegionDAO;

import com.music.business.song.ISongDAO;
import com.music.dto.SongDTO;
import com.music.dto.convert.SongConvert;
import com.music.entity.Category;
import com.music.entity.Listens;
import com.music.entity.Musician;
import com.music.entity.Region;
import com.music.entity.SingSong;
import com.music.entity.Singer;
import com.music.entity.Song;
import com.music.repository.ListenRepository;
import com.music.repository.SongRepository;

@Controller
@RequestMapping("/song")
public class SongController {
	@Autowired
	private ISongDAO songDAO;
	
	@Autowired
	private IListenDAO listenDAO;
	
	@Autowired
	private SongConvert songConvert;
	
	@Autowired
	private IRegionDAO regionDAO;
	
	@Autowired
	private ICategoryDAO categoryDAO;
	
	
	
	
	
	@GetMapping
	private String song(@RequestParam("id") String id,Model model) {
		Long songId=Long.parseLong(id);
		Song song=songDAO.findOneById(songId);
		Listens listen=new Listens();
		listen.setSong(song);
		listenDAO.save(listen);
		int count=listenDAO.countBySong(song);
		
		List<Singer> listSingers=new ArrayList<Singer>();
		for(SingSong s:song.getListSingSong()) {
			List<SingSong> newList=songDAO.getNewestSong(s.getSinger());
			Singer temp=s.getSinger();
			temp.setListSingSong(newList);
			listSingers.add(temp);
		}
		
		
		//Lấy danh sách bài hát theo thể loại (<= 5 bài)
		Category category=song.getCategory();
		category.setListSongs(songDAO.getNewestSong(category));
		
		Musician musician=song.getMusician();
		musician.setListSong(songDAO.getNewestSong(musician));
		model.addAttribute("listSingers",listSingers);
		model.addAttribute("musician",musician);
		model.addAttribute("category",category);
		model.addAttribute("count",count);
		model.addAttribute("song",songConvert.toDTO(song));
		System.out.print(song.getPlayUrl());
		return "web/song/song";
	}
	
	@GetMapping("/all")
	private String allSong(Model model) {
		List<Song> listSongs=songDAO.findAll();
	
		List<SongDTO> listSongDTO=new ArrayList<SongDTO>();
		for(Song s:listSongs) {
			listSongDTO.add(songConvert.toDTO(s));
		}
		List<Region> listRegions=regionDAO.findAll();
		Collections.reverse(listSongDTO);
		model.addAttribute("listRegions",listRegions);
		model.addAttribute("listSongs",listSongDTO);
		return "web/song/allsong";
	}
	
	
	@GetMapping("/category")
	private String category(Model model,@RequestParam("id") String categoryId) {
		Category category=categoryDAO.findOneById(Long.parseLong(categoryId));
		List<Song> list=songDAO.findByCategory(category);
		List<Region> listRegions=regionDAO.findAll();
		List<SongDTO> listSongs=new ArrayList<>();
		for(Song s:list) {
			SongDTO song=songConvert.toDTO(s);
			listSongs.add(song);
			
		}
		Collections.reverse(listSongs);
		model.addAttribute("cateid", categoryId);
		model.addAttribute("listRegions",listRegions);
		model.addAttribute("listSongs",listSongs);
		return "web/song/categorySong";

	}
}
