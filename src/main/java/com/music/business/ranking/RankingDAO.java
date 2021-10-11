package com.music.business.ranking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.dto.RankingObject;
import com.music.entity.RankingTable;
import com.music.entity.Region;
import com.music.entity.SongRank;
import com.music.repository.RankingTableRepository;
import com.music.repository.SongRankRepository;
import com.music.repository.SongRepository;

@Service
public class RankingDAO implements IRankingDAO {
	@Autowired
	private RankingTableRepository rankTableRepo;
	@Autowired
	private SongRankRepository songRankRepo;
	@Autowired
	private SongRepository songRepo;
	@Override
	public boolean existMonthRank(int month, int year,Region region) {
		if(rankTableRepo.findOneByMonthAndYearAndRegion(month,year,region)!=null) {
			return true;
		}
		return false;
	}

	@Override
	public void createRegionRankingTable(Region region,int month,int year) {
		RankingTable rankTable=new RankingTable();
		rankTable.setRegion(region);
		rankTable.setMonth(month);
		rankTable.setYear(year);
		rankTableRepo.save(rankTable);
		System.out.println(month);
		String startDate=year+"-"+month+"-"+"01";
		String endDate=year+"-"+(month+1)+"-"+"01";
		List<SongRank> listSongRank=new ArrayList<SongRank>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = null;
		try {
			sDate = sdf.parse(startDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date eDate = null;
		try {
			eDate = sdf.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<RankingObject> list=songRankRepo.getBestSongs(region.getId(),sDate,eDate);
		for(int i=0;i<list.size();i++) {
			SongRank s=new SongRank();
			s.setRankNumber(i+1);
			s.setRankTable(rankTable);
			s.setSong(songRepo.findOneById(list.get(i).getSong_id()));
			s.setListenNumber(list.get(i).getCount());
			listSongRank.add(s);
		}
		songRankRepo.saveAll(listSongRank);
	}

	@Override
	public RankingTable getRankByRegionAndTime(Region region, int month, int year) {
		return rankTableRepo.findOneByRegionAndMonthAndYear(region, month, year);
	}



}
