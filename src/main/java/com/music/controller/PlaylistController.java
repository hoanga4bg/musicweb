package com.music.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.music.business.account.IAccountDAO;
import com.music.business.listens.IListenDAO;
import com.music.business.playlist.IPlayListDAO;
import com.music.business.song.ISongDAO;
import com.music.dto.SongDTO;
import com.music.dto.convert.SongConvert;
import com.music.entity.Account;
import com.music.entity.Listens;
import com.music.entity.Musician;
import com.music.entity.PlayList;
import com.music.entity.SingSong;
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
	public String allPlaylist(@RequestParam("page") String page,Model model) {
		int currentPage=0;
		int limit =10;
		try {
			currentPage=Integer.parseInt(page);
		}
		catch(Exception e){
			currentPage=0;
		}
		Pageable pageable=PageRequest.of(currentPage-1, limit);
		List<PlayList> listPlayLists=playListDAO.findAllByAccount(accountDAO.findByUsername("admin"),pageable);
		int totalPage=((int) Math.ceil((playListDAO.totalItem(accountDAO.findByUsername("admin"))*1.0/limit)));
		model.addAttribute("listPlaylists", listPlayLists);
		model.addAttribute("page",currentPage);
		model.addAttribute("totalPage", totalPage);
		return "web/playlist/allPlaylist";
	}
	
	@GetMapping
	public String playList(Model model,@RequestParam("playid") String playlistId,@RequestParam("songid") String songId) {
		PlayList playlist=playListDAO.findById(Long.parseLong(playlistId));
		if(playlist.getSongInPlayLists().size()==0) {
			List<SongDTO> listSongs=new ArrayList<SongDTO>();
			model.addAttribute("playlist", playlist);
			model.addAttribute("listSongs", listSongs);
			model.addAttribute("playingSong", new SongDTO());
		}
		else {
			if(songId==null||songId.equals("")) {
				
				long id=playlist.getSongInPlayLists().get(0).getSong().getId();
				return "redirect:/playlist?playid="+playlistId+"&songid="+id;
			}
			Song song=songDAO.findOneById(Long.parseLong(songId));
			List<SongDTO> listSongs=new ArrayList<SongDTO>();
			for(SongInPlayList s:playlist.getSongInPlayLists()) {
				listSongs.add(songConvert.toDTO(s.getSong()));
			}
			
			// Thêm lượt nghe cho bài hát
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
			
			List<Singer> listSingers=new ArrayList<Singer>();
			for(SingSong s:song.getListSingSong()) {
				List<SingSong> newList=songDAO.getNewestSong(s.getSinger());
				Singer temp=s.getSinger();
				temp.setListSingSong(newList);
				listSingers.add(temp);
			}
			
			//Gợi ý bài hát
			List<Song> reSongs=songDAO.recommendSong(accountDAO.getLogingAccount(), song);
			
			//Tối đa 10 bài hát
			if(reSongs.size()<10) {
				for(Singer singer:listSingers) {
					if(reSongs.size()<10) {
						for(SingSong ss:singer.getListSingSong()) {
							if(reSongs.size()<10) {
								if((ss.getSong().getId()!=song.getId())&&(reSongs.contains(ss.getSong())==false)) {
									reSongs.add(ss.getSong());
								}
							}
						}
					}
				}
			}
			
			List<SongDTO> recommendSongs=new ArrayList<SongDTO>();
			for(Song s:reSongs) {
				recommendSongs.add(songConvert.toDTO(s));
			}
			
			//Lấy danh sách playlist
			List<PlayList> listPlaylists=playListDAO.findAllByAccount(accountDAO.getLogingAccount());
			
			model.addAttribute("playlist", playlist);
			model.addAttribute("listSongs", listSongs);
			model.addAttribute("playingSong", songConvert.toDTO(song));
			model.addAttribute("recommendSong", recommendSongs);
			model.addAttribute("listPlaylists", listPlaylists);
		}
		return "web/playlist/playlist";
	}

	@GetMapping("/autoComplete")
	@ResponseBody
	public List<String> playlistName(@RequestParam(value = "term" , required = false, defaultValue = "") String term) {
		List<PlayList> list=playListDAO.findByAccountAndNameContain(accountDAO.findByUsername("admin"),term);
		List<String> names=new ArrayList<String>();
		for(PlayList play:list) {
			names.add(play.getName());
		}
		return names;
	}
	
	@GetMapping("/search")
	public String search(@RequestParam("playlist") String playlist,Model model) {
		List<PlayList> listPlaylists=new ArrayList<PlayList>();
		listPlaylists=playListDAO.findByAccountAndNameContain(accountDAO.findByUsername("admin"),playlist);
		model.addAttribute("listPlaylists", listPlaylists);
		return "web/playlist/allPlaylist";
		
	}
}
