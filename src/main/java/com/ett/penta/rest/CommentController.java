package com.ett.penta.rest;

import java.time.LocalDateTime;
import java.util.List;

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

import com.ett.penta.model.Comment;
import com.ett.penta.model.Post;
import com.ett.penta.rest.util.HeaderUtil;
import com.ett.penta.service.CommentService;
import com.ett.penta.service.PostService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/comment")
public class CommentController {
	private static final String ENTITY_NAME = "post";
	private static final Integer pageSize = 10;
	 @Autowired
	    private CommentService commentService;
	  @PostMapping("/create")
	    public ResponseEntity <Comment> save(@RequestBody Comment t)  {
		  LocalDateTime lt 
          = LocalDateTime.now();
		  t.setDate(lt);
	        Comment comment = commentService.save(t);
	        if (comment == null)
	            return ResponseEntity.noContent().build();
	        return ResponseEntity.created(null).body(comment);
	    }
	  @GetMapping("/")
	    public ResponseEntity<List<Comment>> findall(@RequestParam(value = "page") Integer page){
		  Pageable paging = PageRequest.of(page, pageSize);
	        Page<Comment> list = commentService.findAll(paging);
	        HttpHeaders h = new HttpHeaders();
	        return  ResponseEntity.accepted().headers(h).body(list.getContent());
	    }
	  @GetMapping("/post/{id}")
	    public ResponseEntity<List<Comment>> findByPost(@PathVariable Long id,@RequestParam(value = "page") Integer page){
		  Pageable paging = PageRequest.of(page, pageSize);
	        Page<Comment> list = commentService.findByPost(id, paging);
	        HttpHeaders h = new HttpHeaders();
	        return  ResponseEntity.accepted().headers(h).body(list.getContent());
	    }
	  @DeleteMapping("/{id}")
		public ResponseEntity<String> deleteComment(@PathVariable Long id) {
			ResponseEntity<String> Resultat;
			try {
				commentService.deleteComment(id);
				Resultat = ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, Long.toString(id)))
						.build();
			}catch(Exception e){
				return ResponseEntity.notFound().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, Long.toString(id)))
						.build();
			}
			return Resultat;
		}
}
