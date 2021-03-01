package com.ett.penta.rest;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ett.penta.domain.CommentPost;
import com.ett.penta.model.Post;
import com.ett.penta.rest.util.HeaderUtil;
import com.ett.penta.service.PostService;


@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:4203")
@RestController
@RequestMapping("/api/post")
public class PostController {
	private static final String ENTITY_NAME = "post";
	private static final Integer pageSize = 100;
	 @Autowired
	    private PostService postService;
	  @PostMapping("/create")
	    public ResponseEntity <Post> save(@RequestBody Post p)  {
		  LocalDateTime lt 
          = LocalDateTime.now();
		  p.setDate(lt);
	        Post post = postService.save(p);
	        if (post == null)
	            return ResponseEntity.noContent().build();
	        return ResponseEntity.created(null).body(post);
	    }
	  @GetMapping("/")
	    public ResponseEntity<List<Post>> findall(@RequestParam(value = "page") Integer page){
		  Pageable paging = PageRequest.of(page, pageSize);
	        Page<Post> list = postService.findAll(paging);
	        HttpHeaders h = new HttpHeaders();
	        return  ResponseEntity.accepted().headers(h).body(list.getContent());
	    }
	  @GetMapping("/comment")
	    public ResponseEntity<List<CommentPost>> findallPostComment(@RequestParam(value = "page") Integer page){
		  Pageable paging = PageRequest.of(page, pageSize);
	        List<CommentPost> list = postService.findAllPostComment(paging);
	        HttpHeaders h = new HttpHeaders();
	        return  ResponseEntity.accepted().headers(h).body(list);
	    }
	  @GetMapping("/post/{id}")
	    public ResponseEntity<List<Post>> findByUser(@PathVariable Long id,@RequestParam(value = "page") Integer page){
		  Pageable paging = PageRequest.of(page, pageSize);
	        Page<Post> list = postService.findByUser(id, paging);
	        HttpHeaders h = new HttpHeaders();
	        return  ResponseEntity.accepted().headers(h).body(list.getContent());
	    }
		@DeleteMapping("/{id}")
		public ResponseEntity<String> deletePost(@PathVariable Long id) {
			ResponseEntity<String> Resultat;
			try {
				postService.deletePost(id);
				Resultat = ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, Long.toString(id)))
						.build();
			}catch(Exception e){
				return ResponseEntity.notFound().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, Long.toString(id)))
						.build();
			}
			return Resultat;
		}
}
