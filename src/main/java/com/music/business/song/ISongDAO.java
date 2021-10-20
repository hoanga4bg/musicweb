package com.music.business.song;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.music.entity.*;

public interface ISongDAO {

	public List<Song> findAll();

	public void save(Song song);

	public Song findOneById(Long id);

	public void deleteById(Long id);

	public List<Song> findBySongNameContain(String likeString);

	public List<Song> findByName(String songName);

	public List<Song> findByCategory(Category category);

	public List<Song> getNewestSong(Category category);

	public List<SingSong> getNewestSong(Singer singer);

	public List<Song> getNewestSong(Musician musician);
	
	
	public List<Song> recommendSong(Account account,Song playingSong);
	public List<Song> findAll(Pageable pageable);
	public List<Song> findByCategory(Category category,Pageable pageable);
	public int totalItem();
	public int totalCategoryItem(Category category);

	public List<Song> findBySingerAndMusician(Singer singer, Musician musician);
	public List<Song> findByCategoryAndMusician(Category category, Musician musician);
	public List<Song> findByCategoryAndSinger(Category category, Singer singer);
	public List<Song> findBySinger(Singer singer);
	public List<Song> findByMusician(Musician musician);
	public List<Song> findByCategoryAndSingerAndMusician(Category category, Singer singer,Musician musician);
}
