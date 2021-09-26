package com.music.business.playlist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.entity.Account;
import com.music.entity.PlayList;
import com.music.entity.Song;
import com.music.entity.SongInPlayList;
import com.music.repository.PlayListRepository;
import com.music.repository.SongInPlayListRepository;

@Service
public class PlayListDAO implements IPlayListDAO{

	@Autowired
	private PlayListRepository playListRepository;
	@Autowired
	private SongInPlayListRepository songPlayRepo;
	@Override
	public void save(PlayList playList) {
		playListRepository.save(playList);
		
	}

	@Override
	public void deleteById(Long id) {
		PlayList playlist=playListRepository.findOneById(id);
		
		List<SongInPlayList> list=songPlayRepo.findByPlayList(playlist);
		songPlayRepo.deleteInBatch(list);
		playListRepository.deleteById(id);
		
	}

	@Override
	public List<PlayList> findAll() {
		List<PlayList> list=playListRepository.findAll();
		return list;
	}

	@Override
	public List<PlayList> findAllByAccount(Account account) {
		List<PlayList> list=playListRepository.findAllByCreateBy(account);
		return list;
	}

	@Override
	public void addSongToPlaylist(Song song, PlayList playlist) {
		SongInPlayList sipl=new SongInPlayList();
		sipl.setPlayList(playlist);
		sipl.setSong(song);
		songPlayRepo.save(sipl);
		
	}

	@Override
	public PlayList findById(Long id) {
		
		return playListRepository.findOneById(id);
	}

	@Override
	public void deleteSongFromPlayList(Song song,PlayList playlist) {
		SongInPlayList sipl=songPlayRepo.findOneBySongAndPlayList(song,playlist);
		songPlayRepo.deleteById(sipl.getId());
		
	}

}
