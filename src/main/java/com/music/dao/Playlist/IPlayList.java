package com.music.dao.Playlist;

import java.util.List;

import com.music.entity.Account;
import com.music.entity.PlayList;

public interface IPlayList {
	public void save(PlayList playList);
	public void deleteById(Long id);
	public List<PlayList> findAll();
	public List<PlayList> findAllByAccount(Account account);
}
