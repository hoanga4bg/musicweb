package com.music.business.pay;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.entity.PayHistory;
import com.music.repository.PayHistoryRepository;
@Service
public class PayDAO implements IPayDAO{
	
	@Autowired
	private PayHistoryRepository payRepo;
	@Override
	public PayHistory save(PayHistory pay) {
		
		return payRepo.save(pay);
	}

	@Override
	public List<PayHistory> findAll() {

		return payRepo.findAll();
	}

	@Override
	public List<PayHistory> findByDateBetween(Date startDate, Date endDate) {
		
		return payRepo.findByPayDateBetween(startDate,endDate);
	}
	
	
	
}
