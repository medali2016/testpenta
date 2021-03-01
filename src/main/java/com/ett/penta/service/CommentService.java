package com.ett.penta.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ett.penta.model.Comment;
import com.ett.penta.model.Post;
import com.ett.penta.repository.CommentRepository;
import com.ett.penta.repository.PostRepository;
@Service
@Transactional
public class CommentService {
	@Autowired
	private  PostRepository postRepository;
	@Autowired
	private  CommentRepository commentRepository;
	private final Logger log = LoggerFactory.getLogger(UserService.class);
	public Comment save(Comment comment) {
		log.debug("Request to save User : {}", comment);
		Comment result = commentRepository.save(comment);
		return result; 
	}
	public void deleteComment(long id) {
		commentRepository.deleteById(id);	
	}
	public void deleteCommentBatch(List<Comment> list) {
		commentRepository.deleteInBatch(list);	
	}
	public Page<Comment> findAll(Pageable pageable) {
		System.out.println("Get all comments..."); 
		return commentRepository.findAll(pageable);
	}
	public Page<Comment> findByPost(Long userId,Pageable pageable) {
		System.out.println("Get all comments..."); 
		return commentRepository.findByPost(userId,pageable);
	}
	public List<Comment> findByPost(Long userId) {
		System.out.println("Get all comments by post..."); 
		return commentRepository.findByPost(userId);
	}
}
