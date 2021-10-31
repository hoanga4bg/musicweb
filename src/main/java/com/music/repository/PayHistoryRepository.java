package com.music.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.PayHistory;

public interface PayHistoryRepository extends JpaRepository<PayHistory, Long>{

	List<PayHistory> findByPayDateBetween(Date startDate, Date endDate);

}
