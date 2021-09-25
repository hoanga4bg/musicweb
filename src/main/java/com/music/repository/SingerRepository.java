package com.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Singer;

public interface SingerRepository extends JpaRepository<Singer, Long>{

	Singer findOneById(Long id);

	Singer findOneByName(String name);

}
