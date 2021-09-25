package com.music.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class SongInPlayList {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "song_id")
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private Song song;
	
	@ManyToOne
	@JoinColumn(name = "play_id")
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private PlayList playList;
}
