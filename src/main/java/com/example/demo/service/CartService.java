package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.example.demo.entity.CartItem;
import com.example.demo.repository.CartRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepo;
	
	public CartItem addItem(CartItem item ) {
		return cartRepo.save(item);
	}
	
	public List<CartItem> getAllItems() {
		return cartRepo.findAll();
	}
	
	public void deleteItem(Long id) {  
		cartRepo.deleteById(id);
	}

	public CartItem updateQuantity(Long id, int quantity) {
		CartItem item = cartRepo.findById(id).orElseThrow();
		item.setQuantity(quantity);
		return cartRepo.save(item);
	}

	public double getTotal() {
		return cartRepo.findAll().stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
	}
}
