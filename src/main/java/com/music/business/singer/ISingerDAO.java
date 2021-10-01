package com.music.business.singer;

import java.util.List;

import com.music.entity.Category;
import com.music.entity.Singer;

public interface ISingerDAO {
	public List<Singer> findAll();
	public Singer findOneByName(String name);
	public Singer findOneById(Long id);
	public void save(Singer singer);
	public void deleteById(Long id);

}
