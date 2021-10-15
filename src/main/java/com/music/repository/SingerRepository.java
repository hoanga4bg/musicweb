package com.music.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Singer;

public interface SingerRepository extends JpaRepository<Singer, Long>{

	Singer findOneById(Long id);

	Singer findOneByName(String name);

	List<Singer> findAllByOrderByIdDesc(Pageable pageble);

}
