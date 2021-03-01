package com.ett.penta.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "post")
public class Post {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    @NotNull
	    @Column(name = "title", length = 60)
	    private String title;

	    @Column(name = "description",length=10485760)
	    private String description;
	    @Column
	    private LocalDateTime date;
	    @ManyToOne
	    @NotNull
	    private User user;
	    
	    
		public Post() {
			super();
			// TODO Auto-generated constructor stub
		}


		public Post(Long id, @NotNull @Size(min = 60, max = 60) String title, String description, LocalDateTime date,
				User user) {
			super();
			this.id = id;
			this.title = title;
			this.description = description;
			this.date = date;
			this.user = user;
		}


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getTitle() {
			return title;
		}


		public void setTitle(String title) {
			this.title = title;
		}


		public String getDescription() {
			return description;
		}


		public void setDescription(String description) {
			this.description = description;
		}


		public LocalDateTime getDate() {
			return date;
		}


		public void setDate(LocalDateTime date) {
			this.date = date;
		}


		public User getUser() {
			return user;
		}


		public void setUser(User user) {
			this.user = user;
		}
	    
	    
}
