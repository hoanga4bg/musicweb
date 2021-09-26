package com.music;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.music.business.musician.IMusicianDAO;
import com.music.business.musician.MusicianDAO;
import com.music.business.singer.ISingerDAO;
import com.music.entity.Musician;
import com.music.entity.SingSong;
import com.music.entity.Singer;
import com.music.entity.Song;

@Controller
@RequestMapping("/admin/singer")
public class AdminSingerController {
	@Autowired
	private ISingerDAO singerDAO;
	
	@GetMapping
	public String singerHome(Model model) {
		List<Singer> listSingers=singerDAO.findAll();
		Collections.reverse(listSingers);
		model.addAttribute("listSingers", listSingers);
		
		return "admin/singer/singerHome";
	}
	
	@GetMapping("/add")
	public String addSinger(Model model) {
		model.addAttribute("singer",new Singer());
		return "admin/singer/addSinger";
	}
	
	
	@PostMapping("/add")
	public String addSinger(Singer singer) {
		singerDAO.save(singer);
		return "redirect:/admin/singer";
	}
	
	@GetMapping("/edit")
	public String editSinger(Model model,@RequestParam("id") String id) {
		Singer singer=singerDAO.findOneById(Long.parseLong(id));
		model.addAttribute("singer",singer);
		return "admin/singer/addSinger";
	}
	
	
	@GetMapping("/delete")
	public String deleteSinger(Model model,@RequestParam("id") String id) {
		List<SingSong> listSingSongs=singerDAO.findOneById(Long.parseLong(id)).getListSingSong();
		
		if(listSingSongs.size()==0) {
			singerDAO.deleteById(Long.parseLong(id));
			return "redirect:/admin/singer?success=true";
		}
		else{
			return "redirect:/admin/singer?error=true";
		}
	}
	
}
