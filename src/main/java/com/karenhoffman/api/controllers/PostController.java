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

import com.karenhoffman.api.dto.PostProjection;
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

	// private final Authentication auth =
	// SecurityContextHolder.getContext().getAuthentication();

	@PostMapping("/create")
	//@PreAuthorize("isAuthenticated()")
	    public ResponseEntity<?> newPost(@Valid @RequestBody(required = false) Post post, @RequestHeader (name="Authorization") String token, BindingResult bindingResult) throws Exception {
	    	
		try {
			boolean bandera = false;
			Category cat = new Category();
	        if(bindingResult.hasErrors()){
	            return new ResponseEntity<>("Campos mal o email invalido", HttpStatus.BAD_REQUEST);
	        }
	        post.setCreation_date(new Date());       
	        //post.setUser(userService.getByEmail(token.toString().intern()));
	        
	        for (Category category : categoryService.findAll()) {
				if (category.getCategory().equalsIgnoreCase("general")) {
					bandera = true;
				}
			}
	        
	        if (post.getCategory() == null && bandera==false) {
	        	cat.setCategory("general");
	        	categoryService.save(cat);	        	
	        	post.setCategory(cat);
	        } else {
	        	for (Category category : categoryService.findAll()) {
					if(category.getCategory().equals("general")) {
					  post.setCategory(category);
					}
				}
	        	
	        }
	        postService.save(post);
	        return new ResponseEntity<>("Post creado exitosamente", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Ocurrió el siguiente error: " + e.toString(), HttpStatus.CONFLICT);
		}
		
			
	    }

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			// if
			// (postService.getById(id).getUser().equals(userService.getByEmail(auth.getName()).get()))
			// {
			postService.delete(id);
			return new ResponseEntity<>("Post eliminado exitosamente", HttpStatus.OK);
			// } else {
			// return new ResponseEntity<>("El usuario no está autorizado para eliminar el
			// post", HttpStatus.BAD_REQUEST);
			// }

		} catch (Exception e) {
			e.getMessage();
			return new ResponseEntity<>("Ocurrió el siguiente error: " + e.toString(), HttpStatus.CONFLICT);
		}

	}

	@PatchMapping("/update/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> update(@PathVariable Long id,
			@RequestBody(required = false) Post post /* , @RequestHeader (name="Authorization") String token */) {
		try {
			// if
			// (postService.getById(id).getUser().equals(userService.getByEmail(token.toString())))
			// {
			postService.update(id, post);
			return new ResponseEntity<>("Post actualizado correctamente", HttpStatus.OK);
			// } else {
			// return new ResponseEntity<>("El usuario no está autorizado para editar el
			// post", HttpStatus.BAD_REQUEST);
			// }
		} catch (Exception e) {

			return new ResponseEntity<>("Ocurrió el siguiente error: " + e.toString(), HttpStatus.CONFLICT);

		}

	}

	@GetMapping("/list")
	public List<Post> findAll() {

		List<Post> lista = new ArrayList<>();
		lista = (List<Post>) postService.findAll();
		return lista;

	}

	// Trae los posts (mostrando solo los atributos solicitados) ordenados por fecha de creación descendente
	@GetMapping("/ordered_list")
	public List<PostProjection> list() {
		List<PostProjection> correct_list = new ArrayList<>();
		List<Post> lista = (List<Post>) postService.list();
		for (Post post : lista) {
			if(!post.getDeleted()) {
				PostProjection new_post = new PostProjection();
				new_post.setId_post(post.getId_post());
				new_post.setTitle(post.getTitle());
				new_post.setCategory(post.getCategory());
				new_post.setCreation_date(post.getCreation_date());
				new_post.setUrl_image(post.getUrl_image());
				correct_list.add(new_post);
			}
			
		}
		return correct_list;

	}

	@GetMapping("/find")
	public ResponseEntity<?> findByFilter(@RequestParam(required = false) String title,
			@RequestParam(required = false) Long category) {

		try {
			List<Post> lista = new ArrayList<>();
			lista = (List<Post>) postService.findByFilter(title, category);
			return new ResponseEntity<>(lista, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(e.toString(), HttpStatus.CONFLICT);
		}

	}
}
