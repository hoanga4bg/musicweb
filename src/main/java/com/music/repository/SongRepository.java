package com.music.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Song;

public interface SongRepository extends JpaRepository<Song, Long>{

	public Song findOneById(Long id);

	public List<Song> findByNameContainsIgnoreCase(String likeString);

	public List<Song> findByName(String songName);

}
