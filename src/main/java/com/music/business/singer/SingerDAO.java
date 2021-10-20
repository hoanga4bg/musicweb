package com.music.business.singer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.music.entity.Singer;
import com.music.repository.SingerRepository;

@Service
public class SingerDAO implements ISingerDAO{
	@Autowired
	private SingerRepository singerRepo;
	@Override
	public List<Singer> findAll() {
		List<Singer> list=singerRepo.findAll();
		return list;
	}

	@Override
	public Singer findOneByName(String name) {
		return singerRepo.findOneByName(name);
	}

	@Override
	public Singer findOneById(Long id) {
		
		return singerRepo.findOneById(id);
	}

	@Override
	public void save(Singer singer) {
		singerRepo.save(singer);
		
	}

	@Override
	public void deleteById(Long id) {
		singerRepo.deleteById(id);
		
	}

	@Override
	public List<Singer> findAll(Pageable pageble) {
		List<Singer> listSingers=singerRepo.findAllByOrderByIdDesc(pageble);
		return listSingers;
	}

	@Override
	public int totalItem() {
		
		return (int) singerRepo.findAll().size();
	}

	@Override
	public List<Singer> findByName(String name) {
		List<Singer> listSingers=new ArrayList<Singer>();
		listSingers=singerRepo.findByName(name);
		return listSingers;
	}

	@Override
	public List<Singer> findByNameContain(String term){
		List<Singer> listSingers=new ArrayList<Singer>();
		listSingers=singerRepo.findByNameContainsIgnoreCase(term);
		return listSingers;
	}

}
