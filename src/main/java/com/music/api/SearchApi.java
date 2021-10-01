package com.music.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.music.business.account.IAccountDAO;
import com.music.business.song.ISongDAO;
import com.music.entity.Account;
import com.music.entity.SingSong;
import com.music.entity.Singer;
import com.music.entity.Song;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(produces = "application/json")
public class SearchApi {
	@Autowired
	private ISongDAO songDAO;
	@Autowired
	private IAccountDAO accountDAO;
	@RequestMapping(value = "/api/getAccount",method =RequestMethod.GET)
	@ResponseBody
	public List<Account> accounts(){
		return accountDAO.findAll();
	}
		
	
}
