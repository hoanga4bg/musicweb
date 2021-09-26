package com.music.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Account;
import com.music.entity.PlayList;

public interface PlayListRepository extends JpaRepository<PlayList, Long>{

	List<PlayList> findAllByCreateBy(Account account);

	PlayList findOneById(Long id);

}
