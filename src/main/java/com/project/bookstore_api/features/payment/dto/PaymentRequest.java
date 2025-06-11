package com.project.bookstore_api.features.payment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import com.project.bookstore_api.features.payment.model.PaymentMethod;

public record PaymentRequest (
    @NotNull Long orderId,
    @NotNull PaymentMethod paymentMethod,
    @NotNull @Positive  double amount
){}
