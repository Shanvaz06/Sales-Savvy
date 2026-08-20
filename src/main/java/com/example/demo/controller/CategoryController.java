package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryServ;
	
	public CategoryController(CategoryService categoryServ) {
		this.categoryServ = categoryServ;
	}
	
	@PostMapping ("/save")
	public Category saveCategory(@RequestBody Category category) {
		return categoryServ.saveCategory(category);
	}
	
	@PutMapping("/update/{id}")
    public Category updateCategory(@PathVariable Long id,
                                   @RequestBody Category category) {

        Category existing = categoryServ.getCategoryById(id);

        existing.setCategoryName(category.getCategoryName());

        return categoryServ.saveCategory(existing);
    }
	
	@GetMapping("/all") 
		public List<Category> getAllCategories() {
			return categoryServ.getAllCategories();
		}
}
