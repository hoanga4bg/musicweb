package com.music.dao.song;

import java.util.List;

import com.music.entity.*;
public interface ISongDAO{
	public List<Song> findAll();
	public void add(Song song);
	public Song findOneById(Long id);
	public void deleteById(Long id);
}
