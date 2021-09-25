package com.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	Category findOneById(Long id);
	
}
