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

@Entity
@Data
public class SongRank {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int rankNumber;
	private Long listenNumber;
	@ManyToOne
	@JoinColumn(name="song_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Song song;
	
	@ManyToOne
	@JoinColumn(name="table_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private RankingTable rankTable;
	
	

}
