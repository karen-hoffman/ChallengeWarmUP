package com.karenhoffman.api.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.karenhoffman.api.security.entities.User;
import com.karenhoffman.api.security.repositories.UserRepository;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;
    

    public User getByEmail(String email){
        return userRepository.findByEmail(email);
    }
    
    public Optional<User> getById(Long id){
        return userRepository.findById(id);
    }
    /*public Boolean existsByUsuario(String nombreUsuario){
        return userRepository.existsByUsername(nombreUsuario);
    }*/

    public Boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public void save(User usuario){
        userRepository.save(usuario);
    }


}