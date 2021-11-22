package com.karenhoffman.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.karenhoffman.api.entities.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long>{
	
	public Optional<Post> findById(Long id);
	
	@Query(value = "SELECT * FROM Post ORDER BY creation_date DESC", nativeQuery = true)
	public List<Post> list();
	
	@Query(value = "SELECT * FROM Post AS P INNER JOIN Category AS c WHERE p.title = :title1 OR c.id_category = :category1", nativeQuery = true)
	public List<Post> findByFilter(@RequestParam(value= "title1", required= false)  String title1, @RequestParam(value = "category1", required= false) Long category1);
}
