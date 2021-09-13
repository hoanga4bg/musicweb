package com.music.dao.song;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.entity.Song;
import com.music.repository.SongRepository;

@Service
public class SongDAO implements ISongDAO{
	@Autowired
	private SongRepository songRepo;
	@Override
	public List<Song> findAll() {
		
		return songRepo.findAll();
	}

	@Override
	public Song findOneById(Long id) {
		Song song=songRepo.findOneById(id);
		return song;
	}

	@Override
	public void add(Song song) {
		songRepo.save(song);

	}

	@Override
	public void deleteById(Long id) {
		songRepo.deleteById(id);

	}
	
}
