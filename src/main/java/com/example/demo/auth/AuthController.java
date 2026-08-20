package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authServ;
	
	@Autowired
	private JwtUtil jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login") 
	public String login(@RequestBody LoginRequest request) {

	    Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                    request.getEmail(),
	                    request.getPassword()
	            )
	    );

	    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

	    return jwtService.generateToken(
	            userDetails.getUsername(),
	            userDetails.getAuthorities()
	                    .iterator()
	                    .next()
	                    .getAuthority()
	                    .replace("ROLE_", "")
	    );
	}
}
 