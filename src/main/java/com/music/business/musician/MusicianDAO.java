package com.music.business.musician;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

	@Override
	public Musician findOneById(Long id) {
		
		return musicianRepository.findOneById(id);
	}

	@Override
	public List<Musician> findAll(Pageable pageable) {
		List<Musician> listMusicians=musicianRepository.findAllByOrderByIdDesc(pageable);
		return listMusicians;
	}

	@Override
	public int totalItem() {
		// TODO Auto-generated method stub
		return (int) musicianRepository.findAll().size();
	}

	@Override
	public List<Musician> findByNameContain(String term) {
		List<Musician> listMusicians=musicianRepository.findByNameContainsIgnoreCase(term);
		return listMusicians;
	}

	@Override
	public List<Musician> findByName(String musician) {
		List<Musician> listMusicians=musicianRepository.findByName(musician);
		return listMusicians;
	}

}
