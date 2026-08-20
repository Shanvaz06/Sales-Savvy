package com.example.demo.service;

import java.util.List; 

import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;

@Service
public class CategoryService {
	
	private final CategoryRepository categoryRepo;                                                                                                                                                                                               
	
	public CategoryService(CategoryRepository categoryRepo) {
		this.categoryRepo = categoryRepo;
	}
	
	public Category saveCategory(Category category) {
		if(categoryRepo.existsByCategoryName(category.getCategoryName())) {
			throw new RuntimeException("Category already exists");
		}
		
		return categoryRepo.save(category);
	}
	
	public List<Category> getAllCategories() {
		return categoryRepo.findAll();
	}

	public Category getCategoryById(Long id) {
		return categoryRepo.findById(id).orElseThrow();
	}
}
