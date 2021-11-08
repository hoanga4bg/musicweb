package com.music.business.slide;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.entity.Slide;
import com.music.repository.SlideRepository;

@Service
public class SlideDAO implements ISlideDAO{
	
	@Autowired
	private SlideRepository slideRepo;
	@Override
	public Slide save(Slide slide) {
		
		return slideRepo.save(slide);
	}
	
	@Override
	public List<Slide> findAll() {
		// TODO Auto-generated method stub
		return slideRepo.findAll();
	}

	@Override
	public Slide findOneById(Long id) {
		
		return slideRepo.findOneById(id);
	}
	
}
