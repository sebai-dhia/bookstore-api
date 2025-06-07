package com.project.bookstore_api.features.basket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.bookstore_api.features.book.dto.BookResponseDto;

public record BasketItemResponse(
        @JsonProperty("book") BookResponseDto bookResponseDto,
        int quantity,
        double priceAtPurchase
) {}
