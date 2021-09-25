package com.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Musician;

public interface MusicianRepository extends JpaRepository<Musician, Long>{

	Musician findOneById(Long id);

}
