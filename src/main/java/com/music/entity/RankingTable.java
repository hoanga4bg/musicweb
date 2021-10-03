package com.music.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class RankingTable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "region_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Region region;
	private Integer month;
	private Integer year;
	
	@OneToMany(mappedBy = "rankTable")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<SongRank> listSongRanks;
}

