package com.music.dao.listens;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.entity.Account;
import com.music.entity.Listens;
import com.music.entity.Song;
import com.music.repository.ListenRepository;

@Service
public class ListenDAO implements IListenDAO{
	@Autowired
	private ListenRepository listenRepository;
	@Override
	public void save(Listens listen) {
		listenRepository.save(listen);
		
	}

	@Override
	public int countBySong(Song song) {
		List<Listens> list=listenRepository.findBySong(song);
		return list.size();
	}

	@Override
	public List<Listens> findAllByAccount(Account account) {
		// TODO Auto-generated method stub
		return null;
	}

}
