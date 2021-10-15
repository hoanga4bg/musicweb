package com.music.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Musician;

public interface MusicianRepository extends JpaRepository<Musician, Long>{

	Musician findOneById(Long id);

	List<Musician> findAllByOrderByIdDesc(Pageable pageable);

}
