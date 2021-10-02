package com.music.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.music.entity.Category;
import com.music.entity.Musician;
import com.music.entity.Singer;

import lombok.Data;

@Data
public class SongDTO {
	private Long id;
	private String name;
	private String lyrics;
	private Boolean copyRight;
	private String image;
	private String url;
	private String downloadUrl;
	private String playUrl;
	private Category category;
	private Musician musician;
	private List<Singer> listSingers;
	private String singerNames;
}
