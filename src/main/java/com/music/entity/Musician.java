package com.music.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Musician {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String introduction;
	
	@OneToMany(mappedBy = "musician")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Song> listSong;
	
}
