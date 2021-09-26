package com.music.business.playlist;

import java.util.List;

import com.music.entity.Account;
import com.music.entity.PlayList;
import com.music.entity.Song;
import com.music.entity.SongInPlayList;

public interface IPlayListDAO {
	public void save(PlayList playList);
	public void deleteById(Long id);
	public List<PlayList> findAll();
	public List<PlayList> findAllByAccount(Account account);
	public void addSongToPlaylist(Song song,PlayList playlist);
	public void deleteSongFromPlayList(Song song,PlayList playlist);
	public PlayList findById(Long id);
	
}
