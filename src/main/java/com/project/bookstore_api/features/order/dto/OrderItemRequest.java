package com.project.bookstore_api.features.order.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record OrderItemRequest (
        @NotNull(message = "Book ID is required") Long bookId,
        @Positive(message = "Quantity must be positive") int quantity,
        @PositiveOrZero(message = "Price must be positive") double priceAtPurchase)
{}
