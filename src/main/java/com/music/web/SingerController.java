package com.music.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.music.dao.Singer.ISingerDAO;
import com.music.entity.Singer;


@Controller
@RequestMapping("/singer")
public class SingerController {
	
	@Autowired
	private ISingerDAO singerDAO;
	
	@GetMapping
	public String singerHome(Model model) {
		List<Singer> listSinger=singerDAO.findAll();
		model.addAttribute("listSinger",listSinger);
		return "singer";
	}
	
	
	@PostMapping("/add")
	public String addSinger(Singer singer) {
		singerDAO.save(singer);
		return "redirect:/singer";
	}
	
	@GetMapping("/delete")
	public String deleteSinger(@RequestAttribute("id") String id) {
	
		singerDAO.deleteById(Long.parseLong(id));
		return "redirect:/singer";
	}
	
	
	
}
