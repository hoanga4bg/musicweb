package com.music;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.music.dao.song.ISongDAO;
import com.music.entity.Song;
import com.music.repository.SongRepository;
@Controller
public class HomeController {
	
	@Autowired
	private ISongDAO songDAO;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	private String home(Model model) {
		List<Song> listSongs = new ArrayList<Song>();
		listSongs= songDAO.findAll();
		model.addAttribute("listSongs",listSongs);
		return "home";
	}
	
	@RequestMapping(value = "/test",method = RequestMethod.GET)
	private String test() {
		return "test";
	}
}
