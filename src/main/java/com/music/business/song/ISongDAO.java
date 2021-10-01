package com.music.business.song;

import java.util.List;

import javax.transaction.Transactional;

import com.music.entity.*;

public interface ISongDAO {
	public List<Song> findAll();
	
	@Transactional
	public void save(Song song);

	public Song findOneById(Long id);
	@Transactional
	public void deleteById(Long id);

	public List<Song> findBySongNameContain(String likeString);

	public List<Song> findByName(String songName);

	public List<Song> findByCategory(Category category);
	
	public List<Song> getNewestSong(Category category);
	public List<SingSong> getNewestSong(Singer singer);
	public List<Song> getNewestSong(Musician musician);
}
