package com.music.dao.Comment;

import java.util.List;



import com.music.entity.Comment;
import com.music.entity.Song;

public interface ICommentDAO {
	public void save(Comment comment);
	public void deleteById(Long id);
	public List<Comment> findAllBySong(Song s);
	
}
