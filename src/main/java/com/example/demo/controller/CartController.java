package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CartItem;
import com.example.demo.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartServ;
	
	@PostMapping("/add")
	public CartItem add(@RequestBody CartItem item) {
		return cartServ.addItem(item);
	}
	
	@GetMapping("/all")
	public List<CartItem> all() {
		return cartServ.getAllItems();
	}
	
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		cartServ.deleteItem(id);
		return "Item removed from cart";
	}
	
	@PutMapping("/update/{id}/{quantity}")
	public CartItem updateQuantity(@PathVariable Long id, @PathVariable int quantity) {
		return cartServ.updateQuantity(id, quantity);
	}
	
	@GetMapping("/total")
	public double getTotal() {
		return cartServ.getTotal();
	}
}
