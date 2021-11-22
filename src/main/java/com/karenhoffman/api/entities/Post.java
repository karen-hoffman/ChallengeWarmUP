package com.karenhoffman.api.entities;


import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.URL;

import com.karenhoffman.api.security.entities.User;

@Entity
@SQLDelete(sql = "UPDATE Post SET deleted = true WHERE id_post = ?")
@Where(clause = "deleted = false")
public class Post {
	
	 @Id
	 @GeneratedValue(strategy =  GenerationType.IDENTITY)
	 private Long id_post;
	 private String title;
	 private String content;
	 @URL
	 @Column(nullable = true)
	 private String url_image;
	 @Temporal(TemporalType.DATE)
	 private Date creation_date;
	 @ManyToOne(cascade = CascadeType.PERSIST)
	 private Category category;
	 @ManyToOne(cascade = CascadeType.PERSIST)
	 private User user;
	 private boolean deleted;
	 
	public Post() {
		
	}
	
	public Post(Long id_post, String title, String content, @URL String url_image, Date creation_date,
			Category category, User user, Boolean deleted) {
		super();
		this.id_post = id_post;
		this.title = title;
		this.content = content;
		this.url_image = url_image;
		this.creation_date = creation_date;
		this.category = category;
		this.user = user;
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Post [id_post=" + id_post + ", title=" + title + ", content=" + content + ", url_image=" + url_image
				+ ", creation_date=" + creation_date + ", category=" + category + ", user=" + user + ", deleted="
				+ deleted + "]";
	}

	public Long getId_post() {
		return id_post;
	}
	
	public void setId_post(Long id_post) {
		this.id_post = id_post;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getUrl_image() {
		return url_image;
	}


	public void setUrl_image(String url_image) {
		this.url_image = url_image;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Date getCreation_date() {
		return creation_date;
	}
	
	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, content, creation_date, deleted, id_post, title, url_image, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return Objects.equals(category, other.category) && Objects.equals(content, other.content)
				&& Objects.equals(creation_date, other.creation_date) && Objects.equals(deleted, other.deleted)
				&& Objects.equals(id_post, other.id_post) && Objects.equals(title, other.title)
				&& Objects.equals(url_image, other.url_image) && Objects.equals(user, other.user);
	}

	
	
	
	 
}
