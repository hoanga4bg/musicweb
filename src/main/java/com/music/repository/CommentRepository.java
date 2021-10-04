package com.music.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.entity.Comment;
import com.music.entity.Song;

public interface CommentRepository extends JpaRepository<Comment, Long>{

	List<Comment> findAllBySong(Song s);

	List<Comment> findBySongOrderById(Song song);

	List<Comment> findBySongOrderByIdDesc(Song song);

}
