package com.karenhoffman.api.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.web.servlet.oauth2.resourceserver.JwtDsl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.karenhoffman.api.entities.Category;
import com.karenhoffman.api.entities.Post;
import com.karenhoffman.api.security.entities.User;
import com.karenhoffman.api.security.jwt.JwtProvider;
import com.karenhoffman.api.security.services.UserService;
import com.karenhoffman.api.services.CategoryService;
import com.karenhoffman.api.services.PostService;

@RestController
@RequestMapping("/posts")
@CrossOrigin
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;
	
	private JwtProvider jwtProvider;
	
	
	//private final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	@PostMapping("/create")
	//@PreAuthorize("isAuthenticated()")
	    public ResponseEntity<?> newPost(@Valid @RequestBody(required = false) Post post, @RequestHeader (name="Authorization") String token, BindingResult bindingResult) throws IOException {
	    	boolean bandera = false;
	    	Category cat = new Category();
	        if(bindingResult.hasErrors()){
	            return new ResponseEntity<>("Campos mal o email invalido", HttpStatus.BAD_REQUEST);
	        }
	        post.setCreation_date(new Date());
	       
	        post.setUser(userService.getByEmail(token.toString().intern()));
	        if (post.getCategory() == null) {
	        	
	        	cat.setCategory("general");
	        	categoryService.save(cat);
	        	post.setCategory(cat);
	        } else {
	        	for (Category category : categoryService.findAll()) {
					if(post.getCategory().equals(category)) {
					  bandera = true;
					  cat = category;
					}
				}
	        	if (bandera == true) {
	        		post.setCategory(cat);
	        	} else {
	        		cat.setCategory(post.getCategory().getCategory());
	        		post.setCategory(cat);
	        		
	        	}
	        }
	        postService.save(post);
	       
	        return new ResponseEntity<>("Post creado exitosamente", HttpStatus.CREATED);
	    }
	
	  @DeleteMapping("/delete/{id}")
	  //@PreAuthorize("isAuthenticated()")
	  public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			//if (postService.getById(id).getUser().equals(userService.getByEmail(auth.getName()).get())) {
				postService.delete(id);
				return new ResponseEntity<>("Post eliminado exitosamente", HttpStatus.OK);
			//} else {
				//return new ResponseEntity<>("El usuario no está autorizado para eliminar el post", HttpStatus.BAD_REQUEST);
			//}
			
		} catch (Exception e) {
			e.getMessage();
			return new ResponseEntity<>("Ocurrió un error inesperado", HttpStatus.CONFLICT);
		}
	    
	  }
	  
	  @PatchMapping("/update/{id}")
	  //@PreAuthorize("isAuthenticated()")
	  public ResponseEntity<?> update(@PathVariable Long id, @RequestBody(required = false) Post post /*, @RequestHeader (name="Authorization") String token*/) {
		 try {
			// if (postService.getById(id).getUser().equals(userService.getByEmail(token.toString()))) {
				  postService.update(id, post); 
				  return new ResponseEntity<>("Post actualizado correctamente", HttpStatus.OK);
			//  } else {
			//	  return new ResponseEntity<>("El usuario no está autorizado para editar el post", HttpStatus.BAD_REQUEST);
			//  }
		 } catch (Exception e) {
			
			 return new ResponseEntity<>(e.toString(), HttpStatus.CONFLICT);
			 
		 }
		
		 
	   
	  }
	  
	  @GetMapping("/list")
	  public List<Post> findAll() {
			
		  List<Post >lista  = new ArrayList<>();
		  lista = (List<Post>) postService.findAll();
			return lista;
		   
		  }
	  
	  //Trae los posts ordenados por fecha de creación descendente
	  //Debo implementar serializable para seleccionar los atributos que se solicitan en el ejercicio
	  @GetMapping("/listar")
	  public List<Post> list() {
		  
		List<Post> lista  = new ArrayList<>();
		lista = (List<Post>) postService.list();
		return lista;
		
		   
	  }
	  
	  @GetMapping("/busqueda")
	  public  ResponseEntity<?> findByFilter(@RequestParam(required= false) String title, @RequestParam(required= false) Long category) {
		
		  try {
			  		List<Post> lista  = new ArrayList<>();			
					lista = (List<Post>) postService.findByFilter(title, category);
					return new ResponseEntity<>(lista , HttpStatus.OK);
				
		
		  } catch (Exception e) {
			  return new ResponseEntity<>(e.toString(), HttpStatus.CONFLICT); 
		  }
	  
	  } 
}
