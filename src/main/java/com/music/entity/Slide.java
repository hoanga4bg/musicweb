package com.music.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Slide {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String image;
}
