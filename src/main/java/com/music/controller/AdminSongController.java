package com.music.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.music.business.category.ICategoryDAO;
import com.music.business.musician.IMusicianDAO;
import com.music.business.region.IRegionDAO;
import com.music.business.singer.ISingerDAO;
import com.music.business.song.ISongDAO;
import com.music.dto.SongDTO;
import com.music.dto.convert.SongConvert;
import com.music.entity.Category;
import com.music.entity.Musician;
import com.music.entity.Region;

import com.music.entity.Singer;
import com.music.entity.Song;

@Controller
@RequestMapping("/admin/song")
public class AdminSongController {
	@Autowired
	private ISongDAO songDAO;
	@Autowired
	private ISingerDAO singerDAO;
	
	@Autowired
	private IMusicianDAO musicianDAO;
	
	@Autowired
	private ICategoryDAO categoryDAO;
	
	@Autowired
	private IRegionDAO regionDAO;
	
	@Autowired
	private SongConvert songConvert;
	@GetMapping
	@Transactional
	public String songHome(@RequestParam("category") String categoryId,@RequestParam("singer") String singerId,@RequestParam("musician") String musicianId, Model model) {
		List<Singer> listSingers=singerDAO.findAll();
		List<Musician> listMusicians=musicianDAO.findAll();
		List<Region> listRegions=regionDAO.findAll();
		Singer singer=null;
		Category category=null;
		Musician musician=null;
		List<Song> listSongs=new ArrayList<Song>();
		if((categoryId.equals("")) && (singerId.equals("")) && (musicianId.equals(""))) {
			listSongs=songDAO.findAll();
			Collections.reverse(listSongs);
			
			
			
		}
		else {
			if(categoryId.equals("") && (singerId.equals("")==false) && (musicianId.equals("")==false)) {
				singer=singerDAO.findOneById(Long.parseLong(singerId));
				musician=musicianDAO.findOneById(Long.parseLong(musicianId));
				listSongs=songDAO.findBySingerAndMusician(singer,musician);
			}
			else if((categoryId.equals("")==false)&& (singerId.equals("")) && (musicianId.equals("")==false)) {
				category=categoryDAO.findOneById(Long.parseLong(categoryId));
				musician=musicianDAO.findOneById(Long.parseLong(musicianId));
				listSongs=songDAO.findByCategoryAndMusician(category, musician);
				
			}
			else if((categoryId.equals("")==false)&& (singerId.equals("")==false) && musicianId.equals("")) {
				singer=singerDAO.findOneById(Long.parseLong(singerId));
				category=categoryDAO.findOneById(Long.parseLong(categoryId));
				listSongs=songDAO.findByCategoryAndSinger(category, singer);
				
			}
			else if((categoryId.equals("")==false) && singerId.equals("") && musicianId.equals("")) {
				category=categoryDAO.findOneById(Long.parseLong(categoryId));
				listSongs=songDAO.findByCategory(category);
				
			}
			else if(categoryId.equals("") && (singerId.equals("")==false) && musicianId.equals("")) {
				singer=singerDAO.findOneById(Long.parseLong(singerId));
				listSongs=songDAO.findBySinger(singer);
				
			}
			else if(categoryId.equals("") && singerId.equals("") && (musicianId.equals("")==false)) {
				musician=musicianDAO.findOneById(Long.parseLong(musicianId));
				listSongs=songDAO.findByMusician(musician);
				
			}
			else if((categoryId.equals("")==false) && (singerId.equals("")==false) && (musicianId.equals("")==false)) {
				singer=singerDAO.findOneById(Long.parseLong(singerId));
				category=categoryDAO.findOneById(Long.parseLong(categoryId));
				musician=musicianDAO.findOneById(Long.parseLong(musicianId));
				listSongs=songDAO.findByCategoryAndSingerAndMusician(category, singer, musician);
			}
		}
		
		List<SongDTO> listSongDTO=new ArrayList<SongDTO>();
		for(Song s:listSongs) {
			listSongDTO.add(songConvert.toDTO(s));
		}
		model.addAttribute("listSongs", listSongDTO);
		model.addAttribute("listSingers",listSingers);
		model.addAttribute("listRegions",listRegions);
		model.addAttribute("listMusicians",listMusicians);
		return "admin/song/songHome";
	}
	
	@GetMapping("/add")
	@Transactional
	public String addSong(Model model) {
		List<Singer> listSingers=singerDAO.findAll();
		List<Musician> listMusicians=musicianDAO.findAll();
		List<Region> listRegions=regionDAO.findAll();
		SongDTO song=new SongDTO();
		model.addAttribute("song", song);
		model.addAttribute("listSingers",listSingers);
		model.addAttribute("listRegions",listRegions);
		model.addAttribute("listMusicians",listMusicians);
		return "admin/song/addSong";
	}
	
	@PostMapping("/add")
	@Transactional
	public String addSong(SongDTO song) {
		Song s=songConvert.toEntity(song);
		String driverUrl=s.getUrl();
		String []temp=driverUrl.split("/");
		
		String id=temp[temp.length-2];
		String playUrl="https://docs.google.com/uc?export=download&id="+id;
		String downloadUrl="https://drive.google.com/u/0/uc?id="+id+"&export=download";
		s.setPlayUrl(playUrl);
		s.setDownloadUrl(downloadUrl);
		songDAO.save(s);

		return "redirect:/admin/song";
	}
	
	@GetMapping("/search")
	@Transactional
	public String search(Model model, @RequestParam("name") String name) {
		List<Song> listSongs=songDAO.findByName(name);
		Collections.reverse(listSongs);
		model.addAttribute("listSongs", listSongs);
		return "admin/song/songHome";
	}
	
	@GetMapping("/edit")
	@Transactional
	public String edit(Model model, @RequestParam("id") String id) {
		Song s=songDAO.findOneById(Long.parseLong(id));
		SongDTO song=songConvert.toDTO(s);
		List<Singer> listSingers=singerDAO.findAll();
		List<Musician> listMusicians=musicianDAO.findAll();
		List<Region> listRegions=regionDAO.findAll();
		model.addAttribute("song",song);
		model.addAttribute("listSingers",listSingers);
		model.addAttribute("listRegions",listRegions);
		model.addAttribute("listMusicians",listMusicians);
		
		return "admin/song/addSong";
	}
	
	@GetMapping("/delete")
	@Transactional
	public String delete(Model model, @RequestParam("id") String id) {
		songDAO.deleteById(Long.parseLong(id));
		return "redirect:/admin/song";
	}

}
