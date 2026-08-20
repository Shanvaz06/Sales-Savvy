package com.example.demo.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	public User saveUser(User user) {

	    if(userRepo.existsByEmail(user.getEmail())) {
	        throw new RuntimeException("Email already exists");
	    }

	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    return userRepo.save(user);
	}
	
	public List<User> getAllUsers() { 
		return userRepo.findAll();
	}
	
	public User getUserByEmail(String email) {
	    return userRepo.findByEmail(email).orElse(null);
	}
	
	public String deleteUser(Integer id) {

	    if(userRepo.existsById(id)) {
	        userRepo.deleteById(id);
	        return "User deleted successfully";
	    }

	    return "User not found";
	}
	
	public User updateuser(Integer id, User user) {
		
		User existingUser = userRepo.findById(id).orElse(null);
		
		if(existingUser != null) {
			
			existingUser.setName(user.getName());
			existingUser.setEmail(user.getEmail());
			existingUser.setPassword(user.getPassword());
			existingUser.setRole(user.getRole());
			
			return userRepo.save(existingUser);
		}
		
		return null;
	}
	 
	public UserDTO getUserDTOByEmail(String email) {

	    User user = userRepo.findByEmail(email).orElse(null);

	    if(user == null) {
	        throw new ResourceNotFoundException("User not found");
	    }

	    return new UserDTO(
	            user.getId(),
	            user.getName(),
	            user.getEmail(),
	            user.getRole()
	    );
	}
}
