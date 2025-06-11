package com.project.bookstore_api.features.checkout.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CheckoutItemRequest(
        @NotNull(message = "Book ID is required") Long bookId,
        @Positive(message = "Quantity must be positive") int quantity,
        @PositiveOrZero(message = "Price must be positive") double priceAtPurchase)
{}
