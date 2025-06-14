package com.project.bookstore_api.features.payment.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstore_api.features.payment.model.PaymentStatus;
import com.project.bookstore_api.features.payment.service.PaymentService;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
public class CashPaymentControllerImpl implements PaymentController{
    final private PaymentService service;

    @Override
    @PatchMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> assignPaymentStatus(Long paymentId, @RequestParam PaymentStatus status) {
        service.assignPaymentStatus(paymentId, status);
        return ResponseEntity.noContent().build();
    }
}
