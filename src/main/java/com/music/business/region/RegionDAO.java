package com.music.business.region;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.music.entity.Region;
import com.music.repository.RegionRepository;

@Service
public class RegionDAO implements IRegionDAO {

	@Autowired
	private RegionRepository regionRepository;
	@Override
	public void save(Region region) {
		regionRepository.save(region);
		
	}

	@Override
	public void deleteById(Long id) {
		regionRepository.deleteById(id);
		
	}

	@Override
	public Region findOneById(Long id) {
		
		return regionRepository.findOneById(id);
	}

	@Override
	public List<Region> findAll() {
		List<Region> list=regionRepository.findAll();
		return list;
	}

}
