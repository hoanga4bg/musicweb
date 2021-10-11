package com.music.business.favorite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.entity.Account;
import com.music.entity.Favorite;

import com.music.entity.Song;
import com.music.repository.FavoriteRepository;

@Service
public class FavoriteDAO implements IFavoriteDAO{
	
	@Autowired
	private FavoriteRepository favoriteRepository;

	@Override
	public void save(Favorite favorite) {
		favoriteRepository.save(favorite);
		
	}

	@Override
	public Boolean checkFavorite(Account account, Song song) {
		Favorite favorite=favoriteRepository.findByAccountAndSong(account,song);
		if(favorite!=null) {
			return true;
		}
		return false;
	}

	@Override
	public Favorite findByAccountAndSong(Account account, Song song) {
		
		return favoriteRepository.findByAccountAndSong(account, song);
	}

	@Override
	public void delete(Favorite favorite) {
		favoriteRepository.delete(favorite);
		
	}

	@Override
	public List<Favorite> findByAccount(Account account) {
		
		return favoriteRepository.findByAccount(account);
	}

}
