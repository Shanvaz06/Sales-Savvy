package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

	@Query("SELECT SUM(p.amount) FROM Payment p")
	Double getTotalRevenue();

	@Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'SUCCESS'")
	Double sumSuccessPayments();
}
