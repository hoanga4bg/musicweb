package com.music.dao.listens;

import java.util.List;

import com.music.entity.Account;
import com.music.entity.Listens;
import com.music.entity.Song;

public interface IListenDAO {
	public void save(Listens listen);
	public int countBySong(Song song);
	public List<Listens> findAllByAccount(Account account);

}
