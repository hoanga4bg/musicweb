package com.music.business.listens;

import java.util.List;

import com.music.entity.Account;
import com.music.entity.Listens;
import com.music.entity.Region;
import com.music.entity.Song;

public interface IListenDAO {
	public void save(Listens listen);
	public int countBySong(Song song);
	public List<Listens> findAllByAccount(Account account);
	public long statisticByMonthYear(int month,int year);
	public long statisticByRegionId(Long regionId,int month,int year);
}
