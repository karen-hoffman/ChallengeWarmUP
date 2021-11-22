package com.karenhoffman.api.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.karenhoffman.api.entities.Post;
import com.karenhoffman.api.repositories.PostRepository;

@Service
public class PostService {
	
	@Autowired
	public PostRepository postRepository;
	
	public void save(Post post) {
		
		/*Post newpost = new Post();
		newpost.setCategory(post.getCategory());
		newpost.setContent(post.getContent());
		newpost.setCreation_date(post.getCreation_date());
		newpost.setTitle(post.getTitle());
		newpost.setUrl_image(post.getUrl_image());
		newpost.setUser(post.getUser());*/
		
		
		postRepository.save(post);
	}
	
	public void update(Long id, Post post) {
		Post edit_post = postRepository.findById(id).get();
		edit_post.setCategory(post.getCategory());
		edit_post.setContent(post.getContent());
		edit_post.setCreation_date(post.getCreation_date());
		edit_post.setTitle(post.getTitle());
		edit_post.setUrl_image(post.getUrl_image());
		//edit_post.setUser(post.getUser());
		postRepository.save(edit_post);
	}
	
	public void delete(Long id) {
		postRepository.delete(postRepository.findById(id).get());
	}
	
	public Post getById(Long id) {
		return postRepository.findById(id).get();
	}
	
	public List<Post> findAll() {
		List<Post> lista = new ArrayList<>();
		lista = (List<Post>) postRepository.findAll();
		return lista;
	}
	
	//..
	public List<Post> list() {
		List<Post> lista = new ArrayList<>();
		lista = (List<Post>) postRepository.list();
		return lista;
	}
	
	public List<Post> findByFilter(@RequestParam(required= false) String title, @RequestParam(required= false) Long category) {
		List<Post> lista = new ArrayList<>();
		lista = (List<Post>) postRepository.findByFilter(title, category);
		return lista;
	}
	
	
	
	
	public void validate(Post post) throws Exception {
		
		if(post.getCategory() == null) {						
			throw new Exception("El post debe tener una categoría");
		}
		if(post.getContent().isBlank()) {					
			throw new Exception("El post debe tener contenido");
		}
			
		if(post.getUrl_image().isBlank()) {
			post.setUrl_image("https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png");
		}
		
		if(post.getTitle().isBlank()) { 
			throw new Exception("Debe ingresar un titulo");
		}
	}
		
	
	
	
}
