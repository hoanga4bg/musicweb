package com.music.business.listens;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.entity.Account;
import com.music.entity.Listens;
import com.music.entity.Song;
import com.music.repository.ListenRepository;

@Service
public class ListenDAO implements IListenDAO{
	@Autowired
	private ListenRepository listenRepository;
	@Override
	public void save(Listens listen) {
		listenRepository.save(listen);
		
	}

	@Override
	public int countBySong(Song song) {
		List<Listens> list=listenRepository.findBySong(song);
		return list.size();
	}

	@Override
	public List<Listens> findAllByAccount(Account account) {
		
		return listenRepository.findByListenerOrderByIdDesc(account);
	}

	@Override
	public long statisticByMonthYear(int month, int year) {
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String sDate=year+"-"+month+"-"+"01";
			String eDate=year+"-"+(month+1)+"-"+"01";
			
			
			Date startDate=sdf.parse(sDate);
			Date endDate=sdf.parse(eDate);
			
			List<Listens> list=listenRepository.findByListenDateBetween(startDate,endDate);
			return list.size();
		
		} catch (ParseException e) {
			return 0;
		}
	}

	@Override
	public long statisticByRegionId(Long regionId, int month, int year) {
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String sDate=year+"-"+month+"-"+"01";
			String eDate=year+"-"+(month+1)+"-"+"01";
			
			
			Date startDate=sdf.parse(sDate);
			Date endDate=sdf.parse(eDate);
			
			List<Listens> list=listenRepository.findByRegionIdAndListenDateBetween(regionId,startDate,endDate);
			return list.size();
		
		} catch (ParseException e) {
			return 0;
		}
	}

}
