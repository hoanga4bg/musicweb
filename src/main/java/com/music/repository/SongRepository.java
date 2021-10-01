package com.music.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Category;
import com.music.entity.Song;

public interface SongRepository extends JpaRepository<Song, Long>{
	@Transactional
	public Song findOneById(Long id);
	@Transactional
	public List<Song> findByNameContainsIgnoreCase(String likeString);
	@Transactional
	public List<Song> findByName(String songName);
	@Transactional
	public List<Song> findByCategory(Category category);

}
