package com.project.bookstore_api.features.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record BookResponseDto(
        Long id,
        String title,
        String author,
        double price,
        @JsonIgnore int stock
) {}
