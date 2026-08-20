package com.example.demo.service;

import com.example.demo.dto.OrderItemRequest;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public OrderService(
            OrderRepository orderRepo,
            ProductRepository productRepo,
            UserRepository userRepo) {

        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public OrderEntity placeOrder(
            List<OrderItemRequest> items,
            String email) {

        if (items == null || items.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Find logged-in user
        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<OrderItem> orderItems = new ArrayList<>();

        double total = 0;

        for (OrderItemRequest request : items) {

            Product product = productRepo.findById(request.getProductId())
                    .orElseThrow(() ->
                            new RuntimeException("Product not found"));

            int requestedQuantity = request.getQuantity();

            if (requestedQuantity <= 0) {
                throw new RuntimeException("Invalid quantity");
            }

            // Check stock
            if (product.getQuantity() < requestedQuantity) {
                throw new RuntimeException(
                        "Not enough stock for "
                                + product.getProductName());
            }

            // Create order item
            OrderItem orderItem = new OrderItem();

            orderItem.setProduct(product);
            orderItem.setQuantity(requestedQuantity);
            orderItem.setPrice(product.getPrice());

            orderItems.add(orderItem);

            // Calculate total using DB price
            total += product.getPrice() * requestedQuantity;

            // Reduce product stock
            product.setQuantity(
                    product.getQuantity() - requestedQuantity
            );

            productRepo.save(product);
        }

        // Create order
        OrderEntity order = new OrderEntity();

        order.setUser(user);
        order.setItems(orderItems);
        order.setTotalAmount(total);

        return orderRepo.save(order);
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepo.findAll();
    }

    public List<OrderEntity> getMyOrders(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return orderRepo.findByUser(user);
    }
}