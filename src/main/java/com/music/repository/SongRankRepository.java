package com.music.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.music.entity.RankingTable;
import com.music.entity.Region;
import com.music.entity.SongRank;
import com.music.dto.*;
public interface SongRankRepository extends JpaRepository<SongRank, Long> {
	
	@Query(value="SELECT song_id as song_id,COUNT(*) as count\r\n"
			+ "FROM listens AS l\r\n"
			+ "INNER JOIN song  AS s\r\n"
			+ "ON l.song_id=s.id\r\n"
			+ "WHERE l.region_id = ?1 \r\n"
			+ "AND (l.listen_date BETWEEN ?2 AND ?3 )\r\n"
			+ "GROUP BY song_id\r\n"
			+ "ORDER BY count DESC\r\n"
			+ "LIMIT 20", nativeQuery = true)
	public List<RankingObject> getBestSongs(Long regionId,Date startDate,Date endDate);

	@Query(value="SELECT song_id as song_id,COUNT(*) as count\r\n"
			+ "FROM listens AS l\r\n"
			+ "INNER JOIN song  AS s\r\n"
			+ "ON l.song_id=s.id\r\n"
			+ "GROUP BY song_id\r\n"
			+ "ORDER BY count DESC\r\n"
			+ "LIMIT 20", nativeQuery = true)
	public List<RankingObject> getTheMostListenedSongs();
}
