package com.project.bookstore_api.features.payment.service;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.bookstore_api.features.order.model.Order;
import com.project.bookstore_api.features.order.model.OrderStatus;
import com.project.bookstore_api.features.payment.PaymentRepository;
import com.project.bookstore_api.features.payment.model.Payment;
import com.project.bookstore_api.features.payment.model.PaymentStatus;


@Service
@RequiredArgsConstructor
public class CashPaymentServiceImpl implements PaymentService {
    final private PaymentRepository paymentRepository;

    @Transactional
    @Override
    public void assignPaymentStatus(Long paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment Not Found with ID " + paymentId));

        payment.setStatus(status);
        if (status != PaymentStatus.PENDING) {
            payment.setProcessedAt(LocalDateTime.now());
        }

        Order order = payment.getOrder();
        if (order != null) {
            switch (status) {
                case COMPLETED -> order.setStatus(OrderStatus.PAID);
                case FAILED, REFUNDED -> order.setStatus(OrderStatus.CANCELLED);
            }
        }
    }
}
