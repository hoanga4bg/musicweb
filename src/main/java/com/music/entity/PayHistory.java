package com.music.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class PayHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String payType;
	private Date payDate;
	
	@OneToOne
	@JoinColumn(name="account_id")
	private Account payer;
}
