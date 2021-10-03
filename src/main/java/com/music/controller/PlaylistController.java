package com.music.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.music.business.account.IAccountDAO;
import com.music.business.listens.IListenDAO;
import com.music.business.playlist.IPlayListDAO;
import com.music.business.song.ISongDAO;
import com.music.dto.SongDTO;
import com.music.dto.convert.SongConvert;
import com.music.entity.Account;
import com.music.entity.Listens;
import com.music.entity.PlayList;
import com.music.entity.Singer;
import com.music.entity.Song;
import com.music.entity.SongInPlayList;

@Controller
@RequestMapping("/playlist")
public class PlaylistController {
	@Autowired
	private IPlayListDAO playListDAO;
	
	@Autowired
	private IAccountDAO accountDAO;
	
	@Autowired
	private ISongDAO songDAO;
	@Autowired
	private SongConvert songConvert;
	
	@Autowired
	private IListenDAO listenDAO;
	@GetMapping("/all")
	public String allPlaylist(Model model) {
		List<PlayList> listPlayLists=playListDAO.findAllByAccount(accountDAO.findByUsername("admin"));
		Collections.reverse(listPlayLists);
		model.addAttribute("listPlaylists", listPlayLists);
		return "web/playlist/allPlaylist";
	}
	
	@GetMapping
	public String playList(Model model,@RequestParam("playid") String playlistId,@RequestParam("songid") String songId) {
		if(songId==null||songId.equals("")) {
			PlayList playlist=playListDAO.findById(Long.parseLong(playlistId));
			long id=playlist.getSongInPlayLists().get(0).getSong().getId();
			return "redirect:/playlist?playid="+playlistId+"&songid="+id;
		}
		PlayList playlist=playListDAO.findById(Long.parseLong(playlistId));
		Song song=songDAO.findOneById(Long.parseLong(songId));
		List<SongDTO> listSongs=new ArrayList<SongDTO>();
		for(SongInPlayList s:playlist.getSongInPlayLists()) {
			listSongs.add(songConvert.toDTO(s.getSong()));
		}
		Listens listen=new Listens();
		listen.setListenDate(new Date());
		listen.setRegionId(song.getCategory().getRegion().getId());
		listen.setSong(song);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) {
			listenDAO.save(listen);
		}
		else {
			Account account=accountDAO.getLogingAccount();
			listen.setListener(account);
			listen.setListener(account);
			listenDAO.save(listen);
		}
		

		model.addAttribute("playlist", playlist);
		model.addAttribute("listSongs", listSongs);
		model.addAttribute("playingSong", songConvert.toDTO(song));
		return "web/playlist/playlist";
	}

	
}
