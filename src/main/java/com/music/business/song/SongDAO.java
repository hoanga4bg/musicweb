package com.music.business.song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.entity.Category;
import com.music.entity.Musician;
import com.music.entity.SingSong;
import com.music.entity.Singer;
import com.music.entity.Song;
import com.music.repository.SingSongRepository;
import com.music.repository.SongRepository;

@Service
public class SongDAO implements ISongDAO {
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
		Song song = songRepo.findOneById(id);
		return song;
	}

	@Override
	public void save(Song song) {
		List<SingSong> singSongs = new ArrayList<SingSong>();
		singSongs = song.getListSingSong();
		song.setUploadDate(new Date());
		Song s = songRepo.save(song);
		List<SingSong> list = singSongRepo.findBySong(s);
		for (SingSong ss : list) {
			singSongRepo.deleteById(ss.getId());
		}

		for (SingSong ss : singSongs) {
			ss.setSong(s);
			singSongRepo.save(ss);
		}
	}

	@Override
	public void deleteById(Long id) {
		Song s = songRepo.findOneById(id);
		singSongRepo.deleteBySong(s);
		songRepo.deleteById(s.getId());

	}

	@Override
	public List<Song> findBySongNameContain(String likeString) {
		List<Song> list = new ArrayList<Song>();
		list = songRepo.findByNameContainsIgnoreCase(likeString);
		return list;
	}

	@Override
	public List<Song> findByName(String songName) {

		return songRepo.findByName(songName);
	}

	@Override
	public List<Song> findByCategory(Category category) {
		
		return songRepo.findByCategory(category);
	}

	@Override
	public List<Song> getNewestSong(Category category) {
		if(category.getListSongs().size()<=5)
			return category.getListSongs();
		List<Song> list=category.getListSongs();
		Collections.reverse(list);
		return list.subList(0, 5);
	}

	@Override
	public List<SingSong> getNewestSong(Singer singer) {
		List<SingSong> list=singer.getListSingSong();

		
		if(list.size()<=5) {
			return list;
		}
		Collections.reverse(list);
		return list.subList(0, 5);
	}

	@Override
	public List<Song> getNewestSong(Musician musician) {
		if(musician.getListSong().size()<=5)
			return (musician.getListSong());
		List<Song> list=musician.getListSong();
		Collections.reverse(list);
		return list.subList(0, 5);
	}

}
