package com.karenhoffman.api.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.SQLDelete;

@Entity
public class Category {
	
	 @Id
	 @GeneratedValue(strategy =  GenerationType.IDENTITY)
	 private Long id_category;
	 @Column(unique = true)
	 private String category;
	 
	
	 public Category() {
		 
	 }
	 
	public Category(Long id_category, String category) {
		super();
		this.id_category = id_category;
		this.category = category;
	}


	public Long getId_category() {
		return id_category;
	}


	public void setId_category(Long id_category) {
		this.id_category = id_category;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	@Override
	public int hashCode() {
		return Objects.hash(category, id_category);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(category, other.category) && Objects.equals(id_category, other.id_category);
	}
	
	
	
}
