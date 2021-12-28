package com.music.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Lob
	private String content;
	private Date commentDate;
	@ManyToOne
	@JoinColumn(name = "account_id")
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "song_id")
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private Song song;
}

