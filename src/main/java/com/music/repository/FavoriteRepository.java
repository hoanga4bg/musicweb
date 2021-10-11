package com.music.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Account;
import com.music.entity.Favorite;
import com.music.entity.Song;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>{

	Favorite findByAccountAndSong(Account account, Song song);

	List<Favorite> findByAccount(Account account);

}
