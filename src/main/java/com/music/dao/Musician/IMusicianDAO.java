package com.music.dao.Musician;

import java.util.List;

import com.music.entity.Musician;

public interface IMusicianDAO {
	public void save(Musician musician);
	public void deleteById(Long id);
	public List<Musician> findAll();
}
