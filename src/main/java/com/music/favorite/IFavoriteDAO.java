package com.music.favorite;

import com.music.entity.Account;
import com.music.entity.Favorite;
import com.music.entity.Song;

public interface IFavoriteDAO {
	public void save(Favorite favorite);
	public Boolean checkFavorite(Account account,Song song);
	public Favorite findByAccountAndSong(Account account,Song song);
	public void delete(Favorite favorite);
}
