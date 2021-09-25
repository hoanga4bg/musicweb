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

import com.music.dao.Category.ICategoryDAO;
import com.music.dao.Region.IRegionDAO;
import com.music.entity.Category;
import com.music.entity.Region;
import com.music.entity.Song;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {
	@Autowired
	private IRegionDAO regionDAO;
	
	@Autowired
	private ICategoryDAO categoryDAO;
	@GetMapping
	public String musicianHome(Model model) {
		List<Region> listRegions=regionDAO.findAll();
		Collections.reverse(listRegions);
		model.addAttribute("listRegions", listRegions);
		
		return "admin/category/categoryHome";
	}
	
	@GetMapping("/add")
	public String addMusician(Model model) {
		Category category=new Category();
		List<Region> listRegions=regionDAO.findAll();
		model.addAttribute("listRegions", listRegions);
		model.addAttribute("category",category);
		return "admin/category/addCategory";
	}
	
	
	@PostMapping("/add")
	public String addMusician(Category category) {
		categoryDAO.save(category);
		return "redirect:/admin/category";
	}
	
	@GetMapping("/edit")
	public String addMusician(Model model,@RequestParam("id") String id) {
		Category category=categoryDAO.findOneById(Long.parseLong(id));
		List<Region> listRegions=regionDAO.findAll();
		model.addAttribute("listRegions", listRegions);
		model.addAttribute("category",category);
		return "admin/category/addCategory";
	}
	
	
	@GetMapping("/delete")
	public String deleteMusician(Model model,@RequestParam("id") String id) {
		List<Song> listSongs=categoryDAO.findOneById(Long.parseLong(id)).getListSongs();
		
		if(listSongs.size()==0) {
			categoryDAO.deleteById(Long.parseLong(id));
			return "redirect:/admin/category?success=true";
		}
		else{
			return "redirect:/admin/category?error=true";
		}
	}
}
