package com.music.business.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.entity.Account;
import com.music.entity.Report;
import com.music.repository.ReportRepository;

@Service
public class ReportDAO implements IReportDAO {
	
	@Autowired
	private ReportRepository notiRepo;
	@Override
	public void save(Report noti) {
		notiRepo.save(noti);
		
	}

	@Override
	public void deleteById(Long id) {
		notiRepo.deleteById(id);
		
	}

	@Override
	public List<Report> findAll() {
		List<Report> list=notiRepo.findAll();
		return list;
	}

	//Lay cac bao cao chua dc xu ly
	@Override
	public List<Report> findAllUnCheckReport() {
		List<Report> list=notiRepo.findByChecked(false);
		return list;
	}

	@Override
	public Report findById(Long id) {
		return notiRepo.findOneById(id);
	}

}
