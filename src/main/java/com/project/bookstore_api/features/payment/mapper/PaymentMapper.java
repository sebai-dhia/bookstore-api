package com.project.bookstore_api.features.payment.mapper;

import com.project.bookstore_api.features.payment.dto.PaymentResponse;
import com.project.bookstore_api.features.payment.model.Payment;

public interface PaymentMapper {
        PaymentResponse toDto(Payment payment);
        Payment toEntity(PaymentResponse paymentResponse);
}
