package com.music.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.SingSong;
import com.music.entity.Singer;
import com.music.entity.Song;

public interface SingSongRepository extends JpaRepository<SingSong, Long>{

	SingSong findBySingerAndSong(Singer singer, Song song);

	List<SingSong> findBySong(Song s);
	
	@Transactional
	void deleteBySong(Song s);



}
