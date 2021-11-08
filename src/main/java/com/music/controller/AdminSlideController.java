package com.music.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import com.music.business.slide.ISlideDAO;
import com.music.entity.Singer;
import com.music.entity.Slide;
import com.music.repository.SlideRepository;

@Controller
@RequestMapping("/admin/slide")
public class AdminSlideController {
	private final Cloudinary cloudinary = Singleton.getCloudinary();
	@Autowired
	private ISlideDAO slideDAO;
	
	@GetMapping
	public String allSlide(Model model) {
		List<Slide> listSlide=new ArrayList<Slide>();
		listSlide=slideDAO.findAll();
		if(listSlide.size()==0) {
			for(int i=0;i<4;i++) {
				Slide slide=new Slide();
				slide=slideDAO.save(slide);
				listSlide.add(slide);
			}
			
			
		}
		
		model.addAttribute("listSlide", listSlide);
		return "admin/slide/slide";
	}
	
	@GetMapping("/edit")
	public String editSlide(Model model,@RequestParam("id") String id) {
		Slide slide=slideDAO.findOneById(Long.parseLong(id));
		model.addAttribute("slide",slide);
		return "admin/slide/editSlide";
	}
	@PostMapping("/edit")
	public String edit(Slide slide,@RequestParam("fileImage") MultipartFile fileImage) {
		if(fileImage.isEmpty()==false) {
			try {
				Map uploadResult = cloudinary.uploader().upload(fileImage.getBytes(), ObjectUtils.emptyMap());
				String url = uploadResult.get("url").toString();
				slide.setImage(url);
			
			}catch(IOException e) {
				slide.setImage("");
			}
		}
	
		slideDAO.save(slide);
		return "redirect:/admin/slide";
	}
}
