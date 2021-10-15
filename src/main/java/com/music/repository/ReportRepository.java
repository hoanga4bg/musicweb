package com.music.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Account;
import com.music.entity.Report;

public interface ReportRepository extends JpaRepository<Report,Long>{



	Report findOneById(Long id);

	List<Report> findByChecked(boolean b);
	
}
