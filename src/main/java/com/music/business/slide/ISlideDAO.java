package com.music.business.slide;

import java.util.List;

import com.music.entity.Slide;

public interface ISlideDAO {
	public Slide save(Slide slide);
	public List<Slide> findAll();
	public Slide findOneById(Long id);
}
