package com.example.demo.repository;

import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUser(User user);
}