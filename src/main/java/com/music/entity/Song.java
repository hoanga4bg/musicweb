package com.music.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	private String lyrics;
	private Boolean copyRight;
	private String image;
	private String url;
	private String downloadUrl;
	private Date uploadDate;
	@ManyToOne
	@JoinColumn(name = "category_id")
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private Category category;
	
	@ManyToMany(targetEntity = Singer.class)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Singer> listSingers;
	
	@ManyToOne
	@JoinColumn(name = "musician_id")
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private Musician musician;
	
	@ManyToOne
	@JoinColumn(name = "album_id")
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private Album album;
	
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
	
	
	@ManyToMany(targetEntity = PlayList.class)
	private List<PlayList> listPlayLists;
}
