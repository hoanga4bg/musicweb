package com.music.business.report;

import java.util.List;

import com.music.entity.Account;
import com.music.entity.Report;

public interface IReportDAO {
	public void save(Report report);
	public void deleteById(Long id);

	public Report findById(Long id);
	public List<Report> findAll();
	public List<Report> findAllUnCheckReport();
}
