package com.karenhoffman.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.karenhoffman.api.entities.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long>{

}
