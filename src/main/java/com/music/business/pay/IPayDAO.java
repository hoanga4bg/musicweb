package com.music.business.pay;

import java.util.Date;
import java.util.List;

import com.music.entity.PayHistory;

public interface IPayDAO {
	public PayHistory save(PayHistory pay);
	public List<PayHistory> findAll();
	public List<PayHistory> findByDateBetween(Date startDate, Date endDate);
}
