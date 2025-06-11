package com.project.bookstore_api.features.payment.dto;

import java.time.LocalDateTime;

import com.project.bookstore_api.features.payment.model.PaymentStatus;

public record PaymentResult(
        boolean success,
        PaymentStatus status,
        String message,
        LocalDateTime expiration // For pending cash payments
) {}