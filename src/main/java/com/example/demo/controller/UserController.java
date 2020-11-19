package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.model.requests.CreateUserRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User user = userService.findById(id);
		if(user == null){
			log.error("user not found by Id: ".concat(String.valueOf(id)));
			return ResponseEntity.badRequest().build();
		}
		log.info("user found by id: ".concat(String.valueOf(id)));
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable String username) {
		User user = userService.findByUsername(username);
		if(user == null){
			log.error("user not found by name: ".concat(username));
			return ResponseEntity.notFound().build();
		}
		log.info("user found by name: ".concat(username));
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		if(createUserRequest.getPassword().length() < 7 ||
				!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())){
			log.error("user was not created with bad password:".concat(createUserRequest.getPassword()));
			return ResponseEntity.badRequest().build();
		}
		User user = new User(createUserRequest.getUsername(),createUserRequest.getPassword());
		try {
			userService.registerUser(user);
			log.info("user created: ".concat(createUserRequest.getUsername()));
			return ResponseEntity.ok(user);
		}catch (Exception e){
			log.error("user was not created: " + e.getClass() + " : " + e.getCause());
			return ResponseEntity.unprocessableEntity().build();
		}
	}
	
}
