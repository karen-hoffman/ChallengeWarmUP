package com.karenhoffman.api.dto;

import java.util.Date;
import org.hibernate.validator.constraints.URL;
import com.karenhoffman.api.entities.Category;


public class PostProjection {
	
	
		 private Long id_post;
		 private String title;
		 private String url_image;
		 private Date creation_date;
		 private Category category;
		
		 
		 
		public PostProjection() {
			
		}
		
		public PostProjection (Long id_post, String title, @URL String url_image, Date creation_date,
				Category category) {
			this.id_post = id_post;
			this.title = title;
			this.url_image = url_image;
			this.creation_date = creation_date;
			this.category = category;
			
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
		
		
		public String getUrl_image() {
			return url_image;
		}


		public void setUrl_image(String url_image) {
			this.url_image = url_image;
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
		

		@Override
		public String toString() {
			return "PostProjection [id_post=" + id_post + ", title=" + title + ", url_image=" + url_image
					+ ", creation_date=" + creation_date + ", category=" + category + "]";
		}

	
		
		
		 
	}


