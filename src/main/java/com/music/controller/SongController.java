package com.music.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.music.business.account.IAccountDAO;
import com.music.business.category.ICategoryDAO;
import com.music.business.comment.ICommentDAO;
import com.music.business.favorite.IFavoriteDAO;
import com.music.business.listens.IListenDAO;
import com.music.business.playlist.IPlayListDAO;
import com.music.business.region.IRegionDAO;
import com.music.business.report.IReportDAO;
import com.music.business.song.ISongDAO;
import com.music.config.MyUserDetails;
import com.music.dto.SongDTO;
import com.music.dto.convert.SongConvert;
import com.music.entity.Account;
import com.music.entity.Category;
import com.music.entity.Comment;
import com.music.entity.Favorite;
import com.music.entity.Listens;
import com.music.entity.Musician;
import com.music.entity.PlayList;
import com.music.entity.Region;
import com.music.entity.Report;
import com.music.entity.SingSong;
import com.music.entity.Singer;
import com.music.entity.Song;
import com.music.repository.ListenRepository;
import com.music.repository.SongRepository;

@Controller
@RequestMapping("/song")
public class SongController {
	@Autowired
	private ISongDAO songDAO;
	
	@Autowired
	private IListenDAO listenDAO;
	
	@Autowired
	private SongConvert songConvert;
	
	@Autowired
	private IRegionDAO regionDAO;
	
	@Autowired
	private ICategoryDAO categoryDAO;
	
	@Autowired
	private IAccountDAO accountDAO;
	
	@Autowired
	private IFavoriteDAO favoriteDAO;
	@Autowired
	private ICommentDAO commentDAO;
	
	@Autowired
	private IPlayListDAO playListDAO;
	
	@Autowired
	private IReportDAO reportDAO;
	@GetMapping
	private String song(@RequestParam("id") String id,Model model) {
		Long songId=Long.parseLong(id);
		Song song=songDAO.findOneById(songId);
		
		
		int count=listenDAO.countBySong(song);
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

			listenDAO.save(listen);
		}
		List<Singer> listSingers=new ArrayList<Singer>();
		for(SingSong s:song.getListSingSong()) {
			List<SingSong> newList=songDAO.getNewestSong(s.getSinger());
			Singer temp=s.getSinger();
			temp.setListSingSong(newList);
			listSingers.add(temp);
		}
		
		
		//Lấy danh sách bài hát theo thể loại (<= 5 bài)
		Category category=song.getCategory();
		category.setListSongs(songDAO.getNewestSong(category));
		
		Musician musician=song.getMusician();
		musician.setListSong(songDAO.getNewestSong(musician));
		
		
		List<Comment> listComments=new ArrayList<Comment>();
		listComments=commentDAO.getNewest(song);
		
		//Kiểm tra đã thích chưa
		boolean favorite=favoriteDAO.checkFavorite(accountDAO.getLogingAccount(), song);
		//RecommendSong
		List<Song> reSongs=songDAO.recommendSong(accountDAO.getLogingAccount(), song);
		
		//Thêm đủ 10 bài hát
		if(reSongs.size()<10) {
			for(Singer singer:listSingers) {
				if(reSongs.size()<10) {
					//Láy danh sách bài hát của ca sĩ
					for(SingSong ss:singer.getListSingSong()) {
						if(reSongs.size()<10) {
							//Nếu khác bài hát đang phát + chưa có trong danh sách
							if((ss.getSong().getId()!=song.getId())) {
								if((ss.getSong().getId()!=song.getId())&&(reSongs.contains(ss.getSong())==false)) {
									reSongs.add(ss.getSong());
								}
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
		model.addAttribute("id",id);
		model.addAttribute("listSingers",listSingers);
		model.addAttribute("musician",musician);
		model.addAttribute("category",category);
		model.addAttribute("count",count);
		model.addAttribute("song",songConvert.toDTO(song));
		model.addAttribute("listComments", listComments);
		model.addAttribute("favorite", favorite);
		model.addAttribute("recommendSong", recommendSongs);
		model.addAttribute("listPlaylists", listPlaylists);
		return "web/song/song";
	}
	
	@GetMapping("/all")
	private String allSong(@RequestParam("page") String page,Model model) {
		int currentPage=0;
		int limit =10;
		try {
			currentPage=Integer.parseInt(page);
		}
		catch(Exception e){
			currentPage=0;
		}
		Pageable pageable=PageRequest.of(currentPage-1, limit);
		List<Song> listSongs=songDAO.findAll(pageable);
		System.out.print(listSongs.size());
		List<SongDTO> listSongDTO=new ArrayList<SongDTO>();
		for(Song s:listSongs) {
			listSongDTO.add(songConvert.toDTO(s));
		}
		List<Region> listRegions=regionDAO.findAll();
	
		model.addAttribute("listRegions",listRegions);
		model.addAttribute("listSongs",listSongDTO);
		model.addAttribute("page",currentPage);
		model.addAttribute("totalPage", ((int) Math.ceil((songDAO.totalItem()*1.0/limit))));
		return "web/song/allsong";
	}
	
	
	@GetMapping("/category")
	private String category(Model model,@RequestParam("id") String categoryId,@RequestParam("page") String page) {
		int currentPage=0;
		int limit =10;
		try {
			currentPage=Integer.parseInt(page);
		}
		catch(Exception e){
			currentPage=0;
		}
		Pageable pageable=PageRequest.of(currentPage-1, limit);
		Category category=categoryDAO.findOneById(Long.parseLong(categoryId));
		List<Song> list=songDAO.findByCategory(category,pageable);
		List<Region> listRegions=regionDAO.findAll();
		List<SongDTO> listSongs=new ArrayList<>();
		for(Song s:list) {
			SongDTO song=songConvert.toDTO(s);
			listSongs.add(song);
			
		}
		
		model.addAttribute("cateid", categoryId);
		model.addAttribute("listRegions",listRegions);
		model.addAttribute("listSongs",listSongs);
		model.addAttribute("page",currentPage);
		model.addAttribute("totalPage", ((int) Math.ceil((songDAO.totalCategoryItem(category)*1.0/limit))));
		System.out.println((int) Math.ceil((songDAO.totalCategoryItem(category)*1.0/limit)));
		return "web/song/categorySong";

	}
	@PostMapping("/comment")
	public String comment(@RequestParam("id") String id,@RequestParam("comment") String comment) {
		String temp=comment.trim();
		if(temp.equals("")!=true) {
			Song song=songDAO.findOneById(Long.parseLong(id));
			Comment cmt=new Comment();
			cmt.setAccount(accountDAO.getLogingAccount());
			cmt.setContent(comment);
			cmt.setSong(song);
			commentDAO.save(cmt);
		}
		return "redirect:/song?id="+id;
	}

	

	@GetMapping("/favorite")
	@ResponseBody
	public Boolean addFavoriteAPI(@RequestParam("songid") String id) {
		
		Song song=songDAO.findOneById(Long.parseLong(id));
		if(song!=null) {
			Favorite favorite=new Favorite();
			favorite.setSong(song);
			favorite.setAccount(accountDAO.getLogingAccount());
			favoriteDAO.save(favorite);
			return true;
		}
		return false;
	}
	@GetMapping("/favorite/delete")
	@ResponseBody
	public Boolean deleteFavoriteAPI(@RequestParam("songid") String id) {
		System.out.println("Xóa");
		Song song=songDAO.findOneById(Long.parseLong(id));
		if(song!=null) {
			Favorite favorite=favoriteDAO.findByAccountAndSong(accountDAO.getLogingAccount(), song);
			favoriteDAO.delete(favorite);
			return true;
		}
		return false;
	}
	
	@GetMapping("/search")
	public String search(Model model, @RequestParam("name") String name) {
		List<Song> listSongs=songDAO.findByName(name);
		Collections.reverse(listSongs);
		List<SongDTO> listSongDTO=new ArrayList<SongDTO>();
		for(Song s:listSongs) {
			listSongDTO.add(songConvert.toDTO(s));
		}
		model.addAttribute("listSongs", listSongDTO);
		return "web/song/allsong";
	}
	
	@PostMapping("/report")
	public String report(@RequestParam("songid") String songId,@RequestParam("content") String content) {
		Report report=new Report();
		report.setChecked(false);
		report.setContent(content);
		report.setSong(songDAO.findOneById(Long.parseLong(songId)));
		report.setReportDate(new Date());
		reportDAO.save(report);
		return "redirect:/song?id="+songId;
	}
	
	@PostMapping("/download/{uid}")
	@ResponseBody
	public ResponseEntity<?> download(@PathVariable("uid") String uid){
		Account account=accountDAO.findById(Long.parseLong(uid));
		if(account.getDiamond()>0) {
			int currentDiamond=account.getDiamond()-1;
			account.setDiamond(currentDiamond);
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			((MyUserDetails) principal).setDiamond(currentDiamond);
			accountDAO.save(account);
			System.out.println("OK");
			return ResponseEntity.ok("ok");
		}
		else {
			return (ResponseEntity<?>) ResponseEntity.status(500);
		}
	}
}
