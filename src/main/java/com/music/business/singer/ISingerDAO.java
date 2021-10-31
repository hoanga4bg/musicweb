package com.music.business.singer;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.music.entity.Category;
import com.music.entity.Singer;

public interface ISingerDAO {
	public List<Singer> findAll();
	public Singer findOneByName(String name);
	public Singer findOneById(Long id);
	public Singer save(Singer singer);
	public void deleteById(Long id);
	public List<Singer> findAll(Pageable pageble);
	public List<Singer> findByName(String name);
	public int totalItem();
	public List<Singer> findByNameContain(String term);
}
