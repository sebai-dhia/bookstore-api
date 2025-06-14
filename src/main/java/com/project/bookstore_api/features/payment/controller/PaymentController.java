package com.project.bookstore_api.features.payment.controller;

import com.project.bookstore_api.features.payment.model.PaymentStatus;
import org.springframework.http.ResponseEntity;

public interface PaymentController {
    ResponseEntity<Void> assignPaymentStatus(Long paymentId, PaymentStatus status);
}
