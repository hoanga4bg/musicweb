package com.music.business.playlist;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.music.entity.Account;
import com.music.entity.PlayList;
import com.music.entity.Song;
import com.music.entity.SongInPlayList;

public interface IPlayListDAO {
	public PlayList save(PlayList playList);
	public void deleteById(Long id);
	public List<PlayList> findAll();
	public List<PlayList> findAllByAccount(Account account);
	public void addSongToPlaylist(Song song,PlayList playlist);
	public void deleteSongFromPlayList(Song song,PlayList playlist);
	public PlayList findById(Long id);
	public List<PlayList> findAll(Pageable pageable);
	public List<PlayList> findAllByAccount(Account account,Pageable pageable);
	public int totalItem(Account account);
	public List<PlayList> findByNameAndAccount(String playlist,Account account);
	public List<PlayList> findByAccountAndNameContain(Account account,String term);
}
