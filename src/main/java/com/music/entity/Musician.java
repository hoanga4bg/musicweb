package com.music.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
	private String image;
	@Lob
	private String introduction;
	
	@OneToMany(mappedBy = "musician",cascade = CascadeType.REMOVE)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Song> listSong;
	
	public String getImage() {
		if(this.image==null) {
			return null;
		}
		else if(this.image.contains("/")) {
			return this.image;
		}
		else {
			return "/musician-image/"+this.id+"/"+this.image;
		}
	
		
	}
}
