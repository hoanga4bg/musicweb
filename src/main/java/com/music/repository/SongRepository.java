package com.music.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.music.entity.Category;
import com.music.entity.Musician;
import com.music.entity.Song;

public interface SongRepository extends JpaRepository<Song, Long>{
	
	public Song findOneById(Long id);
	
	public List<Song> findByNameContainsIgnoreCase(String likeString);

	public List<Song> findByName(String songName);

	public List<Song> findByCategory(Category category);


	public List<Song> findAllByOrderByIdDesc(Pageable pageable);

	public List<Song> findByCategoryOrderByIdDesc(Category category,Pageable pageable);

	public List<Song> findByCategoryAndMusician(Category category, Musician musician);

	public List<Song> findByMusician(Musician musician);

}
