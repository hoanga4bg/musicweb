package com.music.business.favorite;

import java.util.List;

import com.music.entity.Account;
import com.music.entity.Favorite;
import com.music.entity.Song;

public interface IFavoriteDAO {
	public void save(Favorite favorite);
	public Boolean checkFavorite(Account account,Song song);
	public List<Favorite> findByAccountAndSong(Account account,Song song);
	public void delete(Favorite favorite);
	public List<Favorite> findByAccount(Account account);
	public void deleteAll(List<Favorite> list);
}
