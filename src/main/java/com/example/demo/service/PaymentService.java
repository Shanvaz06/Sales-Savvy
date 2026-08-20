package com.example.demo.service;

import com.example.demo.entity.Payment;
import com.example.demo.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepo;

    public PaymentService(PaymentRepository paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    public Payment createOrder(double amount) throws Exception {

        RazorpayClient client =
                new RazorpayClient("rzp_test_TOoWRSL7X7maK7", "gMshcu4iIXJkYN3Q9PzqFl6m");

        JSONObject options = new JSONObject();
        options.put("amount", (int)(amount * 100)); // paise
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");

        Order order = client.orders.create(options);

        Payment payment = new Payment();
        payment.setRazorpayOrderId(order.get("id"));
        payment.setAmount(amount);
        payment.setStatus("CREATED");

        return paymentRepo.save(payment);
    }

	public Payment markPaymentSuccess(Long id) {
		Payment payment = paymentRepo.findById(id).orElseThrow();
		payment.setStatus("SUCCESS");
		return paymentRepo.save(payment);
	}
}