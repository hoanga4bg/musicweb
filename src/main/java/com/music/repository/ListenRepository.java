package com.music.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Account;
import com.music.entity.Listens;
import com.music.entity.Song;

import lombok.Data;

public interface ListenRepository extends JpaRepository<Listens, Long>{

	List<Listens> findBySong(Song song);

	List<Listens> findByListenerOrderByIdDesc(Account account);

	List<Listens> findByListenDateBetween(Date startDate, Date endDate);

	List<Listens> findByRegionIdAndListenDateBetween(Long regionId, Date startDate, Date endDate);

}
