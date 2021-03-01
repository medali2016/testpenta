package com.ett.penta.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ett.penta.model.Post;
import com.ett.penta.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PagingAndSortingRepository<Post, Long>,JpaSpecificationExecutor<Post> {
  
  @Query("select m from Post m where m.user.id = :userId")
  Page<Post>  findByUser(@Param("userId") Long userId ,Pageable pageable);
}
