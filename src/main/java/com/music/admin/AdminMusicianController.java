package com.music.admin;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.music.dao.Musician.IMusicianDAO;
import com.music.entity.Musician;
import com.music.entity.Song;

@Controller
@RequestMapping("/admin/musician")
public class AdminMusicianController {
	
	@Autowired
	private IMusicianDAO musicianDAO;
	
	@GetMapping
	public String musicianHome(Model model) {
		List<Musician> listMusicians=musicianDAO.findAll();
		Collections.reverse(listMusicians);
		model.addAttribute("listMusicians", listMusicians);
		
		return "admin/musician/musicianHome";
	}
	
	@GetMapping("/add")
	public String addMusician(Model model) {
		model.addAttribute("musician",new Musician());
		return "admin/musician/addMusician";
	}
	
	
	@PostMapping("/add")
	public String addMusician(Musician musician) {
		musicianDAO.save(musician);
		return "redirect:/admin/musician";
	}
	
	@GetMapping("/edit")
	public String addMusician(Model model,@RequestParam("id") String id) {
		Musician musician=musicianDAO.findOneById(Long.parseLong(id));
		model.addAttribute("musician",musician);
		return "admin/musician/addMusician";
	}
	
	
	@GetMapping("/delete")
	public String deleteMusician(Model model,@RequestParam("id") String id) {
		List<Song> listSongs=(List<Song>) musicianDAO.findOneById(Long.parseLong(id)).getListSong();
		if(listSongs.size()==0) {
			musicianDAO.deleteById(Long.parseLong(id));
			return "redirect:/admin/musician?success=true";
		}
		else{
			return "redirect:/admin/musician?error=true";
		}
	}
	
}
