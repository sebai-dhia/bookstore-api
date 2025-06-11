package com.project.bookstore_api.features.checkout.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CheckoutRequest(
        @NotNull(message = "Customer ID is required")
        Long customerId,
        @NotEmpty(message = "Order must have at least one item")
        List< @Valid CheckoutItemRequest> items,
        LocalDateTime createdAt
) {}
