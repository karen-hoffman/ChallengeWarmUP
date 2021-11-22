package com.karenhoffman.api.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.karenhoffman.api.security.dto.JwtDto;
import com.karenhoffman.api.security.dto.NewUser;
import com.karenhoffman.api.security.dto.UserLogin;
import com.karenhoffman.api.security.entities.Rol;
import com.karenhoffman.api.security.entities.User;
import com.karenhoffman.api.security.enums.RolName;
import com.karenhoffman.api.security.jwt.JwtProvider;
import com.karenhoffman.api.security.services.RolService;
import com.karenhoffman.api.security.services.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;
    
   
    @PostMapping("/sign_up")
    public ResponseEntity<?> newUser(@Valid @RequestBody NewUser newUser, BindingResult bindingResult) throws IOException {
    	
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>("Campos mal o email invalido", HttpStatus.BAD_REQUEST);
        }
      
        
        if(userService.existsByEmail(newUser.getEmail())){
            return new ResponseEntity<>("Ese email ya existe", HttpStatus.BAD_REQUEST);
        }
        
        
        User usuario = new User(newUser.getEmail(), passwordEncoder.encode(newUser.getPassword()));

        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolName(RolName.ROLE_USER).get());
        if(newUser.getRoles().contains("admin"))
            roles.add(rolService.getByRolName(RolName.ROLE_ADMIN).get());
        usuario.setRoles(roles);
        userService.save(usuario);
        
        return new ResponseEntity<>("Usuario creado", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody UserLogin userLogin, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity<JwtDto>(HttpStatus.BAD_REQUEST);
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(userLogin.getEmail(),
                                userLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        String email = jwtProvider.getNombreUsuarioFromToken(jwt);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        //ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        //HttpSession session = attr.getRequest().getSession(true);		//Defino la session		
		//session.setAttribute("usuariosession", userLogin);
        return new ResponseEntity<>(jwtDto, HttpStatus.OK);
    }
    
    @PostMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult bindingResult) {
    	if (bindingResult.hasErrors())
            return new ResponseEntity<JwtDto>(HttpStatus.BAD_REQUEST);
    	
    	User user_edit = userService.getById(user.getId_user()).get();
    	if(user_edit == null) {
    		return new ResponseEntity<>("No existe el usuario que desea editar", HttpStatus.NOT_FOUND);
    	} else {
    		user_edit.setEmail(user.getEmail());
    		user_edit.setPassword(passwordEncoder.encode(user.getPassword()));
    		userService.save(user_edit);
    		return new ResponseEntity<>("usuario editado correctamente", HttpStatus.OK);
    	}
    	
    }
}