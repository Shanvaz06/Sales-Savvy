package com.example.demo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
 
import com.example.demo.security.JwtFilter;

@Configuration
public class SecurityConfig {
	
	
	private JwtFilter jwtFilter;
	
	public SecurityConfig(JwtFilter jwtFilter) {
		this.jwtFilter = jwtFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	    http
	        .cors(cors -> {})
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth

	        	    // Public
	        	    .requestMatchers(
	        	        "/auth/**",
	        	        "/users/save",
	        	        "/swagger-ui/**",
	        	        "/v3/api-docs/**"
	        	    ).permitAll()

	        	    // Admin only - product management
	        	    .requestMatchers(
	        	        "/products/save",
	        	        "/products/update/**",
	        	        "/products/delete/**",
	        	        "/categories/**",
	        	        "/dashboard/**"
	        	    ).hasRole("ADMIN")

	        	    // Logged-in users - viewing products
	        	    .requestMatchers(
	        	        "/products/all",
	        	        "/products/search",
	        	        "/products/category/**",
	        	        "/products/*"
	        	    ).authenticated()

	        	    // Logged-in users
	        	    .requestMatchers(
	        	        "/orders/**",
	        	        "/payment/**"
	        	    ).authenticated()

	        	    .anyRequest().authenticated()
	        	);

	    http.addFilterBefore(
	        jwtFilter,
	        UsernamePasswordAuthenticationFilter.class
	    );

	    return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(List.of("http://localhost:5173"));
	    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    configuration.setAllowedHeaders(List.of("*"));
	    configuration.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(
	        AuthenticationConfiguration config) throws Exception {
	    return config.getAuthenticationManager();
	}
}
