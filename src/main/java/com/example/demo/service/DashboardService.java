package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

@Service
public class DashboardService {
	
	private final UserRepository userRepo;
	private final CategoryRepository categoryRepo;
	private final ProductRepository productRepo;
	private final PaymentRepository paymentRepo;
	
	public DashboardService(UserRepository userRepo, CategoryRepository categoryRepo, 
			              ProductRepository productRepo, PaymentRepository paymentRepo) {
		
		this.userRepo = userRepo;
		this.categoryRepo = categoryRepo;
		this.productRepo = productRepo;
		this.paymentRepo = paymentRepo;
	} 
	
	public long getUserCount() {
		return userRepo.count(); 
	}
	
	public long getCategoryCount() {
		return categoryRepo.count();
	}
	
	public long getProductCount() {
		return productRepo.count();
	}
	
	public long getPaymentCount() {
		return paymentRepo.count();
	}
	
	public Double getTotalRevenue() {
		return paymentRepo.getTotalRevenue();
	}
	
	public Map<String, Object> getDashboardData() {
		Map<String, Object> data  = new HashMap<>();
		
		data.put("users", userRepo.count());
		data.put("products", productRepo.count());
		data.put("categories", categoryRepo.count());
		data.put("payments", paymentRepo.count());
		
		Double revenue = paymentRepo.sumSuccessPayments();
		data.put("revenue", revenue == null ? 0 : revenue);
		
		return data;
	}
}
