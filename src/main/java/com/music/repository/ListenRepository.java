package com.music.repository;

import java.util.List;

import javax.persistence.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Listens;
import com.music.entity.Song;

import lombok.Data;

public interface ListenRepository extends JpaRepository<Listens, Long>{

	List<Listens> findBySong(Song song);

}
