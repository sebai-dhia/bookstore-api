package com.project.bookstore_api.book.dto;


public record BookResponseDto(
        Long id,
        String title,
        String author,
        double price,
        int stock
) {}
