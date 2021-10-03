package com.music.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.music.entity.RankingTable;
import com.music.entity.Region;

public interface RankingTableRepository extends JpaRepository<RankingTable, Long>{

	

	RankingTable findOneByMonthAndYearAndRegion(int month, int year, Region region);
	public RankingTable findOneByRegionAndMonthAndYear(Region region, int month, int year);
	
	
	@Query(value="SELECT DISTINCT r.year FROM ranking_table AS r", nativeQuery = true)
	public List<Integer> listYear();
	@Query(value="SELECT DISTINCT r.month FROM ranking_table AS r WHERE r.year=?1", nativeQuery = true)
	public List<Integer> listMonth(int year);
}
