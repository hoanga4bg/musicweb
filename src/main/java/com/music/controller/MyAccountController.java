package com.music.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.music.business.account.IAccountDAO;
import com.music.business.favorite.IFavoriteDAO;
import com.music.business.listens.IListenDAO;
import com.music.business.playlist.IPlayListDAO;
import com.music.business.song.ISongDAO;
import com.music.dto.SongDTO;
import com.music.dto.convert.SongConvert;
import com.music.entity.Favorite;
import com.music.entity.Listens;
import com.music.entity.PlayList;
import com.music.entity.Song;
import com.music.entity.SongInPlayList;

@Controller
@RequestMapping("/myaccount")
public class MyAccountController {
	@Autowired
	private IAccountDAO accountDAO;
	
	@Autowired
	private IPlayListDAO playListDAO;
	@Autowired
	private IListenDAO listenDAO;
	
	@Autowired
	private IFavoriteDAO favoriteDAO;
	@Autowired
	private SongConvert songConvert;
	@Autowired
	private ISongDAO songDAO;
	@GetMapping
	public String myAccount() {
		return "redirect:/myaccount/playlist";
	}
	
	@GetMapping("/playlist")
	public String myPlayList(Model model) {
		List<PlayList> listPlaylists=new ArrayList<PlayList>();
		listPlaylists=playListDAO.findAllByAccount(accountDAO.getLogingAccount());
		model.addAttribute("listPlaylists", listPlaylists);
		return "web/account/playlist";
	}
	
	@GetMapping("/playlist/detail")
	public String playlistDetail(@RequestParam("id") String id,Model model) {
		PlayList playList=playListDAO.findById(Long.parseLong(id));
		List<SongDTO> listSongs=new ArrayList<SongDTO>();
		for(SongInPlayList s:playList.getSongInPlayLists()) {
			listSongs.add(songConvert.toDTO(s.getSong()));
		}
		model.addAttribute("playlist", playList);
		model.addAttribute("listSongs", listSongs);
		return "web/account/detail";
	}
	
	@GetMapping("/history")
	public String myListen(Model model) {
		List<Listens> listListens=new ArrayList<Listens>();
		listListens=listenDAO.findAllByAccount(accountDAO.getLogingAccount());
		List<SongDTO> listSongs=new ArrayList<SongDTO>();
		if(listListens.size()<=10) {
			for(Listens l:listListens) {
				listSongs.add(songConvert.toDTO(l.getSong()));
			}
			
		}
		else {
			for(int i=0;i<10;i++) {
				listSongs.add(songConvert.toDTO(listListens.get(i).getSong()));
			}
		}
		model.addAttribute("listSongs", listSongs);
		return "web/account/history";
	}
	
	@GetMapping("/favorite")
	public String myFavorite(Model model) {
		List<Favorite> listFavorites=new ArrayList<Favorite>();
		listFavorites=favoriteDAO.findByAccount(accountDAO.getLogingAccount());
		List<SongDTO> listSongs=new ArrayList<SongDTO>();
		for(Favorite f:listFavorites) {
			listSongs.add(songConvert.toDTO(f.getSong()));
		}
		model.addAttribute("listSongs", listSongs);

		return "web/account/favorite";
	}
	
	@GetMapping("/favorite/delete")
	public String deleteFavorite(@RequestParam("songid") String id) {
		Song song=songDAO.findOneById(Long.parseLong(id));
		Favorite favorite=favoriteDAO.findByAccountAndSong(accountDAO.getLogingAccount(), song);
		
		favoriteDAO.delete(favorite);
		return "redirect:/myaccount/favorite";
	}
	@PostMapping("/playlist/add")
	public String addPlaylist(@RequestParam("name") String name) {
		PlayList playlist=new PlayList();
		playlist.setCreateBy(accountDAO.getLogingAccount());
		playlist.setCreateDate(new Date());
		playlist.setName(name);
		playListDAO.save(playlist);
		return "redirect:/myaccount/playlist";
	}
	
	@PostMapping("/playlist/deletesong")
	public String deleteSong(@RequestParam("songid") String songid,@RequestParam("playid") String playid) {
		PlayList playlist=playListDAO.findById(Long.parseLong(playid));
		Song song=songDAO.findOneById(Long.parseLong(songid));
		playListDAO.deleteSongFromPlayList(song, playlist);
		return "redirect:/myaccount/playlist/detail?id="+playid;
	}
	
	@PostMapping("/playlist/delete")
	public String deletePlaylist(@RequestParam("playid") String playid) {
		playListDAO.deleteById(Long.parseLong(playid));
		return "redirect:/myaccount/playlist";
	}
	@PostMapping("/playlist/addsong")
	public String addSongToPlaylist(@RequestParam("songid") String songid,@RequestParam("playid") String playid) {
		PlayList playlist=playListDAO.findById(Long.parseLong(playid));
		boolean check=false;
		for(SongInPlayList sipl:playlist.getSongInPlayLists()) {
			if(sipl.getSong().getId().equals(Long.parseLong(songid))) {
				check=true;
				break;
			}
		}
		if(check==false) {
			Song song=songDAO.findOneById(Long.parseLong(songid));
			
			playListDAO.addSongToPlaylist(song, playlist);
		}
		return "redirect:/myaccount/playlist/detail?id="+playid;
	}
}
