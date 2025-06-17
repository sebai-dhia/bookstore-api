package com.project.bookstore_api.features.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(name = "Cash Payment")
@RequiredArgsConstructor
public class CashPaymentControllerImpl implements PaymentController{
    final private PaymentService service;

    @Override
    @PatchMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Assign Payment Status")
    public ResponseEntity<Void> assignPaymentStatus(Long paymentId, @RequestParam PaymentStatus status) {
        service.assignPaymentStatus(paymentId, status);
        return ResponseEntity.noContent().build();
    }
}
