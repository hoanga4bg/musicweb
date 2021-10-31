package com.music.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
public class PayHistory {
	@Id
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "account_id")
	private Account account;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date payDate;
	private float fee;
}
