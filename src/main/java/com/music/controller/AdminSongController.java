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
	public String songHome(Model model) {
		List<Song> listSongs=songDAO.findAll();
		Collections.reverse(listSongs);
		List<SongDTO> listSongDTO=new ArrayList<SongDTO>();
		for(Song s:listSongs) {
			listSongDTO.add(songConvert.toDTO(s));
		}
		model.addAttribute("listSongs", listSongDTO);
		
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
	@RequestMapping(value = "/songAutoComplete",method =RequestMethod.GET)
	@ResponseBody
	public List<String> songAutoComplete(@RequestParam(value = "term" , required = false, defaultValue = "") String term){
		List<Song> listSongs=songDAO.findBySongNameContain(term);
		List<String> listNames=new ArrayList<String>();
//		//Get list singer name
//		for(int i=0;i<listSongs.size();i++) {
//			String temp="";
//			List<SingSong> listSingSong=listSongs.get(i).getListSingSong();
//
//			for(int j=0;j<listSingSong.size()-1;j++) {
//				
//				temp+=(listSingSong.get(i).getSinger().getName()+", ");
//			}
//	
//			temp+=(listSingSong.get(listSingSong.size()-1).getSinger().getName());
//		
//			listNames.add(listSongs.get(i).getName() + " - " +temp);
//		}
		Set<String> distinctName=new HashSet<String>();
		for(Song s:listSongs) {
			distinctName.add(s.getName());
		}
		
		
		listNames.addAll(distinctName);
		return listNames;
	}
}
