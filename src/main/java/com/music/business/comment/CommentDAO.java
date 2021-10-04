package com.music.business.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.model.IComment;

import com.music.entity.Comment;
import com.music.entity.Song;
import com.music.repository.CommentRepository;

@Service
public class CommentDAO implements ICommentDAO{
	
	@Autowired
	private CommentRepository commentRepository;
	@Override
	public void save(Comment comment) {
		commentRepository.save(comment);

	}

	@Override
	public void deleteById(Long id) {
		commentRepository.deleteById(id);
	
	}

	@Override
	public List<Comment> findAllBySong(Song s) {
		List<Comment> list=commentRepository.findAllBySong(s);
		return list;
	}

	@Override
	public List<Comment> getNewest(Song song) {
		List<Comment> list=commentRepository.findBySongOrderByIdDesc(song);
		if(list.size()<5) {
			return list;
		}
		return commentRepository.findBySongOrderByIdDesc(song).subList(0, 5);
	}
	
}
