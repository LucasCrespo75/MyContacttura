package com.contacttura.contacttura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contacttura.contacttura.model.User;
import com.contacttura.contacttura.repository.ContactturaRepositoryUser;

@RestController
@RequestMapping ({"/api/user"} )
public class ContactturaControllerUser {
	
	@Autowired
	private ContactturaRepositoryUser repository;

	
// 		List All
		@GetMapping
// 		http://localhost:8090/user
		public List findAll() {
			return repository.findAll();
		}
		
//		Find by id - Buscaa valor pelo ID especifico
		@GetMapping(value = "{id}")
//		http://localhost:8090/user/1			
		public ResponseEntity findById(@PathVariable long id) {
			return repository.findById(id)
					.map(user -> ResponseEntity.ok().body(user))
					.orElse(ResponseEntity.notFound().build());
		}
		
//		Create
		@PostMapping
//		http://localhost:8090/user	
		public User create(@RequestBody User user) {
			user.setPassword(criptoGrafia(user.getPassword()));
			return repository.save(user);
		}
		
//		Update
		@PutMapping(value = "{id}")
//		http://localhost:8090/user/2	
		public ResponseEntity update(@PathVariable long id,  
				@RequestBody User user) {
			return repository.findById(id)
					.map(recordUser -> {
						recordUser.setNome(user.getNome());
						recordUser.setUsername(user.getUsername());
						recordUser.setPassword(criptoGrafia(user.getPassword()));
						recordUser.setAdmin(false);
						
						User updateUser = repository.save(recordUser);
				
					return ResponseEntity.ok().body(updateUser);
					}).orElse(ResponseEntity.notFound().build());			
		}
		
		@DeleteMapping(path = {"/{id}"})
//		http://localhost:8090/user/2
		@PreAuthorize("hasRole ('ADMIN')")
		public ResponseEntity <?> delete(@PathVariable long id){
			return repository.findById(id)
					.map(recordUser -> {
						repository.deleteById(id);
						return ResponseEntity.ok().body("USUARIO DELETADO!!!");
					}).orElse(ResponseEntity.notFound().build());
		}
		
		 
		private String criptoGrafia(String password) {
			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String passwordCriptografado = passwordEncoder.encode(password);
			
			return passwordCriptografado;
			
		}
	
			
			
		
		
}
