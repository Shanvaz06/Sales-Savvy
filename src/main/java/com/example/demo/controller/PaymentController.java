package com.example.demo.controller;

import com.example.demo.dto.PaymentRequest;
import com.example.demo.entity.Payment;
import com.example.demo.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-order")
    public Payment createOrder(@RequestBody PaymentRequest request)
            throws Exception {

        return paymentService.createOrder(request.getAmount());
    }
    
    @PutMapping("/success/{id}")
    public Payment paymentSuccess(@PathVariable Long id) {
    	return paymentService.markPaymentSuccess(id);
    }
}