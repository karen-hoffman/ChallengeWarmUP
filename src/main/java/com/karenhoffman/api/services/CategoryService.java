package com.karenhoffman.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karenhoffman.api.entities.Category;
import com.karenhoffman.api.entities.Post;
import com.karenhoffman.api.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	
	public void save(Category category) {
		
	  categoryRepository.save(category);
	}
	
	public List<Category> findAll() {
		
		  return (List<Category>) categoryRepository.findAll();
		}
}
