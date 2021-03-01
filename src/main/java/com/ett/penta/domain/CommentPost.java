package com.ett.penta.domain;

import java.util.List;

import com.ett.penta.model.Comment;
import com.ett.penta.model.Post;

public class CommentPost {
private Post post;
private List<Comment> comments;

public CommentPost() {
	super();
	// TODO Auto-generated constructor stub
}
public CommentPost(Post post, List<Comment> comments) {
	super();
	this.post = post;
	this.comments = comments;
}
public Post getPost() {
	return post;
}
public void setPost(Post post) {
	this.post = post;
}
public List<Comment> getComments() {
	return comments;
}
public void setComments(List<Comment> comments) {
	this.comments = comments;
}

}
