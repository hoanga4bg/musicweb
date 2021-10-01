package com.music.controller;

import java.util.ArrayList;
import java.util.Collection;
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
import com.music.business.song.ISongDAO;
import com.music.entity.Account;
import com.music.entity.Song;
import com.music.repository.SongRepository;
@Controller
public class HomeController {
	
	@Autowired
	private ISongDAO songDAO;
	@Autowired
	private IAccountDAO accountDAO;
	@RequestMapping(value = "/", method = RequestMethod.GET)
	private String home(Model model) {
		if(accountDAO.findAll().size()==0) {
			Account account=new Account();
			account.setUsername("admin");
			account.setPassword("admin");
			account.setEmail("hoanga4bg@gmail.com");
			account.setStatus(true);
			account.setVip(true);
			accountDAO.save(account);
		}
		Collection<? extends GrantedAuthority> authorities;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        authorities = auth.getAuthorities();
        String myRole = authorities.toArray()[0].toString();
//        System.out.println(myRole);
		if(myRole.equals("ROLE_ADMIN")) {
			return "redirect:/admin/song";
		} 
		List<Song> listSongs = new ArrayList<Song>();
		listSongs= songDAO.findAll();
		model.addAttribute("listSongs",listSongs);
		return "web/home";
	}
	
	
	
	@RequestMapping(value = "/test",method = RequestMethod.GET)
	private String test() {
		return "web/test";
	}
}
