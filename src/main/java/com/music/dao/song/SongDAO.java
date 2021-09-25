package com.music.dao.song;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.entity.SingSong;
import com.music.entity.Song;
import com.music.repository.SingSongRepository;
import com.music.repository.SongRepository;

@Service
public class SongDAO implements ISongDAO{
	@Autowired
	private SongRepository songRepo;
	@Autowired
	private SingSongRepository singSongRepo;
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
	public void save(Song song) {
		List<SingSong> singSongs=new ArrayList<SingSong>();
		singSongs=song.getListSingSong();
		Song s=songRepo.save(song);
		List<SingSong> list=singSongRepo.findBySong(s);
		for(SingSong ss:list) {
			singSongRepo.deleteById(ss.getId());
		}
		
		for(SingSong ss:singSongs) {
			ss.setSong(s);
			singSongRepo.save(ss);
		}
	}
	
	@Override
	public void deleteById(Long id) {
		Song s=songRepo.findOneById(id);
		singSongRepo.deleteBySong(s);
		songRepo.deleteById(s.getId());

	}

	@Override
	public List<Song> findBySongNameContain(String likeString) {
		List<Song> list=new ArrayList<Song>();
		list=songRepo.findByNameContainsIgnoreCase(likeString);
		return list;
	}

	@Override
	public List<Song> findByName(String songName) {
		
		return songRepo.findByName(songName);
	}
	
}
