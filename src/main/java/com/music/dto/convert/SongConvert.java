package com.music.dto.convert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.music.dto.SongDTO;
import com.music.entity.Song;
import com.music.entity.SingSong;
import com.music.entity.Singer;
@Service
public class SongConvert {
	public SongDTO toDTO(Song song) {
		SongDTO songDTO=new SongDTO();
		songDTO.setId(song.getId());
		songDTO.setCategory(song.getCategory());
		songDTO.setCopyRight(song.getCopyRight());
		songDTO.setUrl(song.getUrl());
		songDTO.setMusician(song.getMusician());
		songDTO.setName(song.getName());
		songDTO.setLyrics(song.getLyrics());
		
		songDTO.setPlayUrl(song.getPlayUrl());
		songDTO.setDownloadUrl(song.getDownloadUrl());
		songDTO.setImageShow(song.getImageShow());
		List<Singer> singers=new ArrayList<Singer>();
		String singerNames="";
		for(SingSong s:song.getListSingSong()) {
			singers.add(s.getSinger());
			singerNames+=(s.getSinger().getName()+", ");
		}
		
		songDTO.setListSingers(singers);
		if(singerNames.length()>2) {
			songDTO.setSingerNames(singerNames.substring(0,singerNames.length()-2));
		}
		else {
			songDTO.setSingerNames("");
		}
		return songDTO;
	}
	
	public Song toEntity(SongDTO songDTO) {
		Song song=new Song();
		song.setId(songDTO.getId());
		song.setCategory(songDTO.getCategory());
		song.setCopyRight(songDTO.getCopyRight());
		song.setUrl(songDTO.getUrl());
		song.setMusician(songDTO.getMusician());
		song.setName(songDTO.getName());
		song.setLyrics(songDTO.getLyrics());
	
		song.setDownloadUrl(songDTO.getDownloadUrl());
		song.setPlayUrl(songDTO.getPlayUrl());
		song.setImageShow(songDTO.getImageShow());
		List<SingSong> singSongs=new ArrayList<SingSong>();
		for(Singer s:songDTO.getListSingers()) {
			SingSong temp=new SingSong();
			temp.setSinger(s);
			singSongs.add(temp);
		}
		song.setListSingSong(singSongs);
		return song;
	}
}
