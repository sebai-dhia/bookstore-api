package com.project.bookstore_api.features.checkout.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.project.bookstore_api.features.order.model.OrderStatus;

public record CheckoutResponse(
        Long orderId,
        Long customerId,
        List<CheckoutItemResponse> items,
        OrderStatus status,
        LocalDateTime createdAt) {
}
