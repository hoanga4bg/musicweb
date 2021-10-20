package com.music.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Category;
import com.music.entity.Musician;
import com.music.entity.Singer;
import com.music.entity.Song;

public interface SingerRepository extends JpaRepository<Singer, Long>{

	Singer findOneById(Long id);

	Singer findOneByName(String name);

	List<Singer> findAllByOrderByIdDesc(Pageable pageble);

	List<Singer> findByName(String name);

	List<Singer> findByNameContainsIgnoreCase(String term);


}
