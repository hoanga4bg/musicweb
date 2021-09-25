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
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String content;
	private Date notiDate;
	private Boolean readed;
	@ManyToOne
	@JoinColumn(name = "account_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Account account;
	
}
