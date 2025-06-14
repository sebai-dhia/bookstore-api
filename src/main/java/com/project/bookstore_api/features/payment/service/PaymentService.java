package com.project.bookstore_api.features.payment.service;

import com.project.bookstore_api.features.payment.model.PaymentStatus;

public interface PaymentService {
    void assignPaymentStatus(Long paymentId, PaymentStatus status);

}
