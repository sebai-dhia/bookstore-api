package com.project.bookstore_api.features.order.dto;

import com.project.bookstore_api.features.book.dto.BookResponseDto;

public record OrderItemResponse(
        BookResponseDto bookResponseDto,
        int quantity,
        double priceAtPurchase)
{}
