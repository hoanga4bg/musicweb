package com.music.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	public String addSinger(Singer singer,@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
		if(multipartFile.isEmpty()==false) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			singer.setImage(fileName);
			Singer savedSinger=singerDAO.save(singer);
			String uploadDir = "singer-image/" + savedSinger.getId();
			
			Path uploadPath=Paths.get(uploadDir);
			
			if(!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			
			try(InputStream inputStream=multipartFile.getInputStream()){
			Path filePath=uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath,StandardCopyOption.REPLACE_EXISTING);
			}
			catch(IOException e) {
				throw new IOException("Không tìm thấy file "+ fileName);
			}
		}
		else {
			singerDAO.save(singer);
		}
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
