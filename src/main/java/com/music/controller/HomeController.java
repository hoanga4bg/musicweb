package com.music.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.music.business.account.IAccountDAO;
import com.music.business.musician.IMusicianDAO;
import com.music.business.playlist.IPlayListDAO;
import com.music.business.ranking.IRankingDAO;
import com.music.business.singer.ISingerDAO;
import com.music.business.slide.ISlideDAO;
import com.music.business.song.ISongDAO;
import com.music.dto.SongDTO;
import com.music.dto.convert.SongConvert;
import com.music.entity.Account;
import com.music.entity.Musician;
import com.music.entity.PlayList;
import com.music.entity.Singer;
import com.music.entity.Slide;
import com.music.entity.Song;
import com.music.repository.SongRepository;
@Controller
public class HomeController {
	
	@Autowired
	private ISongDAO songDAO;
	@Autowired
	private IAccountDAO accountDAO;
	
	@Autowired
	private IMusicianDAO musicianDAO;
	
	@Autowired
	private ISingerDAO singerDAO;
	
	@Autowired
	private IPlayListDAO playlistDAO;
	
	@Autowired
	private SongConvert songConvert;
	
	@Autowired
	private IRankingDAO rankingDAO;
	
	@Autowired
	private ISlideDAO slideDAO;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	private String home(Model model) {
		if(accountDAO.findAll().size()==0) {
			Account account=new Account();
			account.setUsername("admin");
			account.setPassword("admin");
			account.setEmail("hoanga4bg@gmail.com");
			account.setStatus(true);
			account.setDiamond(9999999L);
			account.setRole("ROLE_ADMIN");
			accountDAO.save(account);
		}
		Collection<? extends GrantedAuthority> authorities;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        authorities = auth.getAuthorities();
        String myRole = authorities.toArray()[0].toString();

		//Lấy bài hát
		List<Song> listSongs = new ArrayList<Song>();
		listSongs= songDAO.findAll();
		Collections.reverse(listSongs);
		listSongs=(listSongs.size()>10) ? listSongs.subList(0,10):listSongs;
		List<SongDTO> list=new ArrayList<SongDTO>();
		for(Song s: listSongs) {
			list.add(songConvert.toDTO(s));
		}
		
		//Lấy ca sĩ
		List<Singer> listSingers=singerDAO.findAll();
		Collections.reverse(listSingers);
		
		//Lấy nhạc sĩ
		List<Musician> listMusicians=musicianDAO.findAll();
		Collections.reverse(listMusicians);
		
		
		//Lấy playlist
		List<PlayList> listPlaylists=playlistDAO.findAllByAccount(accountDAO.findByUsername("admin"));
		Collections.reverse(listPlaylists);
		
		//Lấy <=20 bài hát nghe nhiều nhất
		List<Song> tops=rankingDAO.getTheMostListenedSongs();
		List<SongDTO> topSongs=new ArrayList<SongDTO>();
		for(Song s:tops) {
			topSongs.add(songConvert.toDTO(s));
		}
		
		List<Slide> listSlides=slideDAO.findAll();
		
		model.addAttribute("listSlides", listSlides);
		model.addAttribute("listSongs",  list);
		model.addAttribute("listSingers",(listSingers.size()>10) ? listSingers.subList(0,10):listSingers);
		model.addAttribute("listMusicians", (listMusicians.size()>10) ? listMusicians.subList(0,10):listMusicians);
		model.addAttribute("listPlaylists", (listPlaylists.size()>10) ? listPlaylists.subList(0,10):listPlaylists);
		model.addAttribute("topSongs", topSongs);
		return "web/home";
	}
	
	
	

}
