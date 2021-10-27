package com.music.business.song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.music.business.account.IAccountDAO;
import com.music.entity.Account;
import com.music.entity.Category;
import com.music.entity.Favorite;
import com.music.entity.Listens;
import com.music.entity.Musician;
import com.music.entity.Rule;
import com.music.entity.SingSong;
import com.music.entity.Singer;
import com.music.entity.Song;
import com.music.repository.RuleRepository;
import com.music.repository.SingSongRepository;
import com.music.repository.SingerRepository;
import com.music.repository.SongRepository;

@Service
public class SongDAO implements ISongDAO {
	@Autowired
	private SongRepository songRepo;
	@Autowired
	private SingSongRepository singSongRepo;

	@Autowired
	private RuleRepository ruleRepository;
	@Autowired
	private SingerRepository singerRepo;
	@Override
	@Transactional
	public List<Song> findAll() {
		return songRepo.findAll();
	}

	@Override
	public Song findOneById(Long id) {
		Song song = songRepo.findOneById(id);
		return song;
	}

	@Override
	public void save(Song song) {
		List<SingSong> singSongs = new ArrayList<SingSong>();
		singSongs = song.getListSingSong();
		song.setUploadDate(new Date());
		Song s = songRepo.save(song);
		List<SingSong> list = singSongRepo.findBySong(s);
		for (SingSong ss : list) {
			singSongRepo.deleteById(ss.getId());
		}

		for (SingSong ss : singSongs) {
			ss.setSong(s);
			singSongRepo.save(ss);
		}
	}

	@Override
	public void deleteById(Long id) {
		Song s = songRepo.findOneById(id);
		singSongRepo.deleteBySong(s);
		songRepo.deleteById(s.getId());

	}

	@Override
	public List<Song> findBySongNameContain(String likeString) {
		List<Song> list = new ArrayList<Song>();
		list = songRepo.findByNameContainsIgnoreCase(likeString);
		return list;
	}

	@Override
	public List<Song> findByName(String songName) {

		return songRepo.findByName(songName);
	}

	@Override
	public List<Song> findByCategory(Category category) {
		List<Song> listSongs=new ArrayList<Song>();
		listSongs=songRepo.findByCategory(category);
		Collections.reverse(listSongs);
		return listSongs;
	}

	@Override
	public List<Song> getNewestSong(Category category) {
		if(category.getListSongs().size()<=5)
			return category.getListSongs();
		List<Song> list=category.getListSongs();
		Collections.reverse(list);
		return list.subList(0, 5);
	}

	@Override
	public List<SingSong> getNewestSong(Singer singer) {
		List<SingSong> list=singer.getListSingSong();

		
		if(list.size()<=5) {
			return list;
		}
		Collections.reverse(list);
		return list.subList(0, 5);
	}

	@Override
	public List<Song> getNewestSong(Musician musician) {
		if(musician.getListSong().size()<=5)
			return (musician.getListSong());
		List<Song> list=musician.getListSong();
		Collections.reverse(list);
		return list.subList(0, 5);
	}

	@Override
	public List<Song> recommendSong(Account account, Song playingSong) {
		List<Favorite> listFavor = account.getListFavor();
		List<Long> favoriteSongs = new ArrayList<Long>();
		List<Listens>  listenHistory=account.getListListens();
		favoriteSongs.add(playingSong.getId());
		
		//Add favorite song to search list
		if(listFavor!=null) {
			
			for (Favorite f : listFavor) {
				favoriteSongs.add(f.getSong().getId());
			}
		}
		if(listenHistory!=null) {
			if(listenHistory.size()<=3) {
				for (Listens l : listenHistory) {
					favoriteSongs.add(l.getSong().getId());
				}
			}
			else {
				for (int i=0;i<3;i++) {
					favoriteSongs.add(listenHistory.get(i).getSong().getId());
				}
			}
		}
		
		Set<Long> recommendSongs = new HashSet<Long>();
		List<Rule> rules = ruleRepository.findAll();

		
		for (Rule rule : rules) {
			//get max 10 song
			if (recommendSongs.size() < 10) {
				String x = rule.getX().substring(0, rule.getX().length() - 1); // Bỏ dấu phẩy ở cuối
				String[] tempX = x.split(",");
			
				// Convert tempX thành list Long
				List<Long> xLong = new ArrayList<Long>();
				for (int i = 0; i < tempX.length; i++) {
					xLong.add(Long.parseLong(tempX[i]));
				}

				if (favoriteSongs.size() > xLong.size()) {
					// Nếu x thỏa mãn thêm y vào danh sách gợi ý
					if (favoriteSongs.containsAll(xLong)) {
						String y = rule.getY().substring(0, rule.getY().length() - 1);
						String[] tempY = y.split(",");

						// Convert tempY thành mảng Long
						List<Long> yLong = new ArrayList<Long>();
						for (int i = 0; i < tempY.length; i++) {
							if(playingSong.getId()!=Long.parseLong(tempY[i])) {
								yLong.add(Long.parseLong(tempY[i]));
							}
						}
						
						// Add all element of yLong to recommend
						recommendSongs.addAll(yLong);
						
					}
				}
			}
		}
		
		if(recommendSongs.size()<=10) {
			if(recommendSongs.size()>0) {
				return songRepo.findAllById(recommendSongs);
			}
			else {
				return new ArrayList<Song>();
			}
		}
		else {
			List<Long> listIds=new ArrayList<Long>();
			listIds.addAll(recommendSongs);
	
			return songRepo.findAllById(listIds.subList(0, 10));
		}

		
	}

	@Override
	public List<Song> findAll(Pageable pageable) {
		List<Song> listSongs=songRepo.findAllByOrderByIdDesc(pageable);
		return listSongs;
	}

	@Override
	public int totalItem() {
		return (int) songRepo.findAll().size();
	}

	@Override
	public List<Song> findByCategory(Category category, Pageable pageable) {
		List<Song> listSongs=songRepo.findByCategoryOrderByIdDesc(category,pageable);
		return listSongs;
	}

	@Override
	public int totalCategoryItem(Category category) {
		
		return (int) songRepo.findByCategory(category).size();
	}

	@Override
	public List<Song> findBySingerAndMusician(Singer singer, Musician musician) {
		
		List<Song> listSongs=new ArrayList<Song>();
		for(SingSong s:singer.getListSingSong()) {
			if(s.getSong().getMusician().getId()==musician.getId()) {
				listSongs.add(s.getSong());
			}
		}
		Collections.reverse(listSongs);
		return listSongs;
	}

	@Override
	public List<Song> findByCategoryAndMusician(Category category, Musician musician) {
		List<Song> listSongs=new ArrayList<Song>();
		listSongs=songRepo.findByCategoryAndMusician(category,musician);
		Collections.reverse(listSongs);
		return listSongs;
	}

	@Override
	public List<Song> findByCategoryAndSinger(Category category, Singer singer) {
		List<Song> listSongs=new ArrayList<Song>();
		for(SingSong s:singer.getListSingSong()) {
			if(s.getSong().getCategory().getId()==category.getId()) {
				listSongs.add(s.getSong());
			}
		}
		Collections.reverse(listSongs);
		return listSongs;
	}

	@Override
	public List<Song> findBySinger(Singer singer) {
		List<Song> listSongs=new ArrayList<Song>();
	
		for(SingSong s:singer.getListSingSong()) {
			listSongs.add(s.getSong());
		}
		return listSongs;
	}

	@Override
	public List<Song> findByMusician(Musician musician) {
		List<Song> listSongs=new ArrayList<Song>();
		listSongs=musician.getListSong();
		return listSongs;
	}

	@Override
	public List<Song> findByCategoryAndSingerAndMusician(Category category, Singer singer, Musician musician) {
		List<Song> listSongs=new ArrayList<Song>();
		System.out.print(singer.getListSingSong().size());
		for(SingSong s:singer.getListSingSong()) {
			if((s.getSong().getCategory().getId()==category.getId()) && (s.getSong().getMusician().getId()==musician.getId())) {
				listSongs.add(s.getSong());
			}
		}
		Collections.reverse(listSongs);
		return listSongs;
	}
	
	
	
}
