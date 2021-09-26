package com.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.PayHistory;

public interface PayHistoryRepository extends JpaRepository<PayHistory, Long>{

}
