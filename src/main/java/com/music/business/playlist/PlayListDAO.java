package com.music.business.playlist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
		List<SongInPlayList> sipl=songPlayRepo.findAllBySongAndPlayList(song,playlist);
		songPlayRepo.deleteAll(sipl);
		
	}

	@Override
	public List<PlayList> findAll(Pageable pageable) {
		List<PlayList> listPlayLists=playListRepository.findAllByOrderByIdDesc(pageable);
		return listPlayLists;
	}

	@Override
	public int totalItem(Account account) {
		// TODO Auto-generated method stub
		return (int) playListRepository.findAllByCreateBy(account).size();
	}

	@Override
	public List<PlayList> findAllByAccount(Account account, Pageable pageable) {
		List<PlayList> list=playListRepository.findAllByCreateByOrderByIdDesc(account,pageable);

		return list;
	}

	@Override
	public List<PlayList> findByNameAndAccount(String playlist, Account account) {
		List<PlayList> list=playListRepository.findByNameAndCreateBy(playlist,account);
		return list;
	}

	@Override
	public List<PlayList> findByAccountAndNameContain(Account account, String term) {
		List<PlayList> list=playListRepository.findByCreateByAndNameContainsIgnoreCase(account,term);
		return list;
	}

}
