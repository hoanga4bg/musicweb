package com.music.dao.Playlist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.entity.Account;
import com.music.entity.PlayList;
import com.music.repository.PlayListRepository;

@Service
public class PlayListDAO implements IPlayList{

	@Autowired
	private PlayListRepository playListRepository;
	@Override
	public void save(PlayList playList) {
		playListRepository.save(playList);
		
	}

	@Override
	public void deleteById(Long id) {
		playListRepository.deleteById(id);
		
	}

	@Override
	public List<PlayList> findAll() {
		List<PlayList> list=playListRepository.findAll();
		return list;
	}

	@Override
	public List<PlayList> findAllByAccount(Account account) {
		List<PlayList> list=playListRepository.findAllByCreateBy(account);
		return list;
	}

}
