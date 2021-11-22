package com.karenhoffman.api.security.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.karenhoffman.api.security.entities.User;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	
	public Optional<User> findById(Long id);
	
    public User findByEmail(String email);
    
    boolean existsByEmail (String email);
}