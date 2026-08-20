package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userServ;
	
	public UserController(UserService userServ) {
		this.userServ = userServ;
	}
	
	@PostMapping("/save")
	public User saveUser(@Valid @RequestBody User user) {
		return userServ.saveUser(user);
	}
	
	@GetMapping("/all")
	public List<User> getAllUsers() {
		return userServ.getAllUsers();
	}
	
	@GetMapping("/email/{email}") 
	public User getUserByEmail(@PathVariable String email) {
		return userServ.getUserByEmail(email); 
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable Integer id) {
		return userServ.deleteUser(id);
	}
	
	@PutMapping("/update/{id}")
	public User updateUser(@PathVariable Integer id, @Valid @RequestBody User user) {
		
		return userServ.updateuser(id,  user);
	}
	
	@GetMapping("/dto/{email}")
	public UserDTO getUserDTO(@PathVariable String email) {
		return userServ.getUserDTOByEmail(email); 
	}
}
