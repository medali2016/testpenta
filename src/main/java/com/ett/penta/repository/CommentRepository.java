package com.ett.penta.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ett.penta.model.Comment;
import com.ett.penta.model.Post;

public interface CommentRepository extends JpaRepository<Comment, Long>, PagingAndSortingRepository<Comment, Long>,JpaSpecificationExecutor<Post> {
	@Query("select m from Comment m where m.post.id = :postId")
	  Page<Comment>  findByPost(@Param("postId") Long postId ,Pageable pageable);
	@Query("select m from Comment m where m.post.id = :postId")
	  List<Comment>  findByPost(@Param("postId") Long postId);
}
