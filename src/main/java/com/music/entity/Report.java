package com.music.entity;

import java.util.Date;

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
public class Report {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String content;
	private Date reportDate;
	private Boolean checked;
	private Date checkDate;
	
	
	@ManyToOne
	@JoinColumn(name = "song_id")
	private Song song;
	
	@ManyToOne
	@JoinColumn(name="account_id")
	private Account account;

	
}
