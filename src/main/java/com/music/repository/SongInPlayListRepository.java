package com.music.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.PlayList;
import com.music.entity.Song;
import com.music.entity.SongInPlayList;

public interface SongInPlayListRepository extends JpaRepository<SongInPlayList, Long>{




	SongInPlayList findOneBySongAndPlayList(Song song, PlayList playlist);

	List<SongInPlayList> findByPlayList(PlayList playlist);

	List<SongInPlayList> findAllBySongAndPlayList(Song song, PlayList playlist);

}
