package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.demo.entity.Product;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepo;

	public ProductService(ProductRepository productRepo) {
		this.productRepo = productRepo;
	}

	public Product saveProduct(Product product) {
		return productRepo.save(product);
	}

	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}

	public Product getProductById(Long id) {
		return productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
	}

	public Product updateProduct(Long id, Product product) {
		Product existing = productRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		if (existing != null) {
			existing.setProductName(product.getProductName());
			existing.setPrice(product.getPrice());
			existing.setQuantity(product.getQuantity());
			existing.setCategory(product.getCategory());

			return productRepo.save(existing);
		}

		return null;
	}

	public void deleteProduct(Long id) {
		productRepo.deleteById(id);
	}

	public Page<Product> getAllProducts(Pageable pageable) {
		return productRepo.findAll(pageable);
	}

	public List<Product> searchProducts(String name) {
		return productRepo.findByProductNameContainingIgnoreCase(name);
	}

	public List<Product> getProductsbyCategory(Long id) {
		return productRepo.findByCategoryId(id);
	}
}
