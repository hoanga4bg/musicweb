package com.music.business.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.entity.Category;
import com.music.entity.Song;
import com.music.repository.CategoryRepository;

@Service
public class CategoryDAO implements ICategoryDAO {
	
	@Autowired
	private CategoryRepository categoryRepo;
	@Override
	public List<Category> findAll() {
		
		return categoryRepo.findAll();
	}

	@Override
	public Category findOneById(Long id) {
		// TODO Auto-generated method stub
		return categoryRepo.findOneById(id);
	}

	@Override
	public void save(Category category) {
		categoryRepo.save(category);
		
	}

	@Override
	public void deleteById(Long id) {
		categoryRepo.deleteById(id);
		
	}



}
