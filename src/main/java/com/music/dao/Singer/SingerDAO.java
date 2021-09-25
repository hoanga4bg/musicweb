package com.music.dao.Singer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

}
