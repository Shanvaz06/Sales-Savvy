package com.example.demo.controller;

import com.example.demo.dto.OrderItemRequest;
import com.example.demo.entity.OrderEntity;
import com.example.demo.service.OrderService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Place order for logged-in user
    @PostMapping("/place")
    public OrderEntity placeOrder(
            @RequestBody List<OrderItemRequest> items,
            Authentication authentication) {

        String email = authentication.getName();

        return orderService.placeOrder(items, email);
    }

    // Admin - all orders
    @GetMapping("/all")
    public List<OrderEntity> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Logged-in user - own orders
    @GetMapping("/my")
    public List<OrderEntity> getMyOrders(
            Authentication authentication) {

        String email = authentication.getName();

        return orderService.getMyOrders(email);
    }
}