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
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String password;
	private String email;
	private String phoneNumber;
	private String role;
	private Boolean vip;
	private Boolean status;

	@OneToMany(mappedBy = "account")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Favorites> listFavor;
	
	
	@OneToMany(mappedBy = "account")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Comment> listComment;
	
	@OneToMany(mappedBy = "listener")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Listens> listListens;
	
	@OneToMany(mappedBy = "createBy")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<PlayList> listPlayLists;
	
	@OneToMany(mappedBy = "account")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Notification> listNoti;

}
