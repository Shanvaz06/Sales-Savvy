package com.example.demo.controller;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductService productServ;
	
	public ProductController(ProductService productServ) {
		this.productServ = productServ;
	}
	
	@PostMapping("/save")
	public Product saveProduct(@Valid @RequestBody Product product) {
		return productServ.saveProduct(product);
	}
	
	@GetMapping("/all")
	public List<Product> getAllProducts() {
		return productServ.getAllProducts(); 
	}
	
	@GetMapping("/{id}")
	public Product getProductById(@PathVariable Long id) {
		return productServ.getProductById(id);
	}
	
	@PutMapping("/update/{id}")
	public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
		return productServ.updateProduct(id, product);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteProduct(@PathVariable Long id) {
		productServ.deleteProduct(id);
		return "Product deleted successfully";
	}
	
	@GetMapping
	public Page<Product> getProducts(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size,
	        @RequestParam(defaultValue = "id") String sortBy) {

	    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

	    return productServ.getAllProducts(pageable);
	}
	
	@GetMapping("/search")
	public List<Product> searchProducts(@RequestParam String name) {
		return productServ.searchProducts(name);
	}
	 
	@GetMapping("/category/{id}")
	public List<Product> getProductsByCategory(@PathVariable Long id) {
		return productServ.getProductsbyCategory(id);
	}
}
