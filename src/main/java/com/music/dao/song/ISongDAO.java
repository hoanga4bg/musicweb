package com.music.dao.song;

import java.util.List;

import com.music.entity.*;
public interface ISongDAO{
	public List<Song> findAll();
	public void save(Song song);
	public Song findOneById(Long id);
	public void deleteById(Long id);
	public List<Song> findBySongNameContain(String likeString);
	public List<Song> findByName(String songName);
}
