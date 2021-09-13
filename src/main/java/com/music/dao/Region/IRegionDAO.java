package com.music.dao.Region;

import java.util.List;

import com.music.entity.Region;

public interface IRegionDAO {
	public void save(Region region);
	public void deleteById(Long id);
	public Region findOneById(Long id);
	public List<Region> findAll();
}
