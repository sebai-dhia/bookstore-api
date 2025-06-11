package com.project.bookstore_api.features.checkout.dto;

public record CheckoutItemResponse(
        Long bookId,
        int quantity,
        double priceAtPurchase)
{}
