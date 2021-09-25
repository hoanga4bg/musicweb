package com.music.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.music.dao.Musician.IMusicianDAO;
import com.music.dao.Singer.ISingerDAO;
import com.music.dao.listens.IListenDAO;
import com.music.dao.song.ISongDAO;
import com.music.entity.Listens;
import com.music.entity.Musician;
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
	

	@GetMapping
	private String song(@RequestParam("id") String id,Model model) {
		Long songId=Long.parseLong(id);
		Song song=songDAO.findOneById(songId);
		
		
		Listens listen=new Listens();
		listen.setSong(song);
		listenDAO.save(listen);
		int count=listenDAO.countBySong(song);
		model.addAttribute("count",count);
		model.addAttribute("song",song);
	
		return "song";
	}
	

}
