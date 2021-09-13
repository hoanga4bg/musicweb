package com.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {

	Region findOneById(Long id);

}
