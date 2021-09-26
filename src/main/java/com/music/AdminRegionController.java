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

import com.music.business.region.IRegionDAO;

import com.music.entity.Category;
import com.music.entity.Region;


@Controller
@RequestMapping("/admin/region")
public class AdminRegionController {
	@Autowired
	private IRegionDAO regionDAO;
	
	@GetMapping
	public String regionHome(Model model) {
		List<Region> listRegions=regionDAO.findAll();
		Collections.reverse(listRegions);
		model.addAttribute("listRegions", listRegions);
		
		return "admin/region/regionHome";
	}
	
	@GetMapping("/add")
	public String addRegion(Model model) {
		Region region=new Region();
		model.addAttribute("region",region);
		return "admin/region/addRegion";
	}
	
	
	@PostMapping("/add")
	public String addRegion(Region region) {
		regionDAO.save(region);
		return "redirect:/admin/region";
	}
	
	@GetMapping("/edit")
	public String editRegion(Model model,@RequestParam("id") String id) {
		Region region=regionDAO.findOneById(Long.parseLong(id));
		model.addAttribute("region",region);
		return "admin/region/addRegion";
	}
	
	
	@GetMapping("/delete")
	public String deleteRegion(Model model,@RequestParam("id") String id) {
		List<Category> listCategories=regionDAO.findOneById(Long.parseLong(id)).getListCategory();
		
		if(listCategories.size()==0) {
			regionDAO.deleteById(Long.parseLong(id));
			return "redirect:/admin/region?success=true";
		}
		else{
			return "redirect:/admin/region?error=true";
		}
	}
	
}
