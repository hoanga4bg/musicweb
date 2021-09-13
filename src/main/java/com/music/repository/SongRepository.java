package com.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Song;

public interface SongRepository extends JpaRepository<Song, Long>{

	public Song findOneById(Long id);

}
