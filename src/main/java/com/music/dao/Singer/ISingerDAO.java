package com.music.dao.Singer;

import java.util.List;

import com.music.entity.Singer;

public interface ISingerDAO {
	public List<Singer> findAll();
	public List<Singer> findByName(String name);
	public Singer findOneById(Long id);
	public void save(Singer singer);
	public void deleteById(Long id);
}
