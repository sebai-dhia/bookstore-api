package com.project.bookstore_api.features.payment.dto;

import java.time.LocalDateTime;

import com.project.bookstore_api.features.payment.model.PaymentMethod;
import com.project.bookstore_api.features.payment.model.PaymentStatus;

public record PaymentResponse(
        Long paymentId,
        PaymentMethod method,
        PaymentStatus status,
        double amount,
        Long orderReference,
        String message,
        LocalDateTime timestamp
) {}