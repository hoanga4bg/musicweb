package com.music.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Song {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	@Lob
	private String lyrics;
	private Boolean copyRight;
	private String image;
	private String url;
	private String playUrl;
	private String downloadUrl;
	private Date uploadDate;

	@ManyToOne
	@JoinColumn(name = "category_id")
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private Category category;
	
	@OneToMany(mappedBy = "song")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<SingSong> listSingSong;
	
	@ManyToOne
	@JoinColumn(name = "musician_id")
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private Musician musician;
	

	@OneToMany(mappedBy = "song")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Listens> listListens;
	
	@OneToMany(mappedBy = "song")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Favorites> listFavors;
	
	@OneToMany(mappedBy = "song")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Comment> listComments;
	
	
	@OneToMany(mappedBy = "song")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<SongInPlayList> songInPlayLists;
	
	
	@OneToMany(mappedBy = "song")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<SongRank> listSongRank;
}
