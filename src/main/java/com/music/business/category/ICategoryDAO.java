package com.music.business.category;

import java.util.List;

import com.music.entity.Category;
public interface ICategoryDAO {
	public List<Category> findAll();
	public Category findOneById(Long id);
	public void save(Category category);
	public void deleteById(Long id);
}
