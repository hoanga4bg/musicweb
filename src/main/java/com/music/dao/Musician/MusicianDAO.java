package com.music.dao.Musician;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.entity.Musician;
import com.music.repository.MusicianRepository;

@Service
public class MusicianDAO implements IMusicianDAO{
	
	@Autowired
	private MusicianRepository musicianRepository;
	@Override
	public void save(Musician musician) {
		musicianRepository.save(musician);
		
	}

	@Override
	public void deleteById(Long id) {
		musicianRepository.deleteById(id);
		
	}

	@Override
	public List<Musician> findAll() {
		List<Musician> list=musicianRepository.findAll();
		return list;
	}

}
