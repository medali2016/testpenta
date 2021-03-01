package com.ett.penta.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ett.penta.domain.CommentPost;
import com.ett.penta.model.Comment;
import com.ett.penta.model.Post;
import com.ett.penta.model.User;
import com.ett.penta.repository.CommentRepository;
import com.ett.penta.repository.PostRepository;
import com.ett.penta.repository.UserRepository;

@Service
@Transactional
public class PostService {
	@Autowired
	private  PostRepository postRepository;
	@Autowired
	private  CommentService commentService;
	
	private final Logger log = LoggerFactory.getLogger(UserService.class);
	public Post save(Post post) {
		log.debug("Request to save User : {}", post);
		//article.isNormalised(false);
		//:userRepositoryElastic.save(user);
		Post result = postRepository.save(post);
		return result; //test branch
		//test to sarray branch
	}
	public void deletePost(long id) {
		List<Comment> list = commentService.findByPost(id);
		commentService.deleteCommentBatch(list);
		postRepository.deleteById(id);	
	}
	public Page<Post> findAll(Pageable pageable) {
		return postRepository.findAll(pageable);
		// return users;
	}
	public List<CommentPost> findAllPostComment(Pageable pageable) {
		System.out.println("Get all posts...");
		List<CommentPost> list = new ArrayList<CommentPost>();
		postRepository.findAll(pageable).getContent().forEach(e->{
			CommentPost pc = new CommentPost(e,commentService.findByPost(e.getId()));
			list.add(pc);
		});
		return list;
		// return users;
	}
	public Page<Post> findByUser(Long userId,Pageable pageable) {
		System.out.println("Get all posts..."); 
		return postRepository.findByUser(userId,pageable);
		// return users;
	}
}
