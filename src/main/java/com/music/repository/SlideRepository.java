package com.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Slide;

public interface SlideRepository extends JpaRepository<Slide, Long>{

	Slide findOneById(Long id);
	
}
