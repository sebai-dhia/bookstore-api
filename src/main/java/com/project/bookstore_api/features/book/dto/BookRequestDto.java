package com.project.bookstore_api.features.book.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record BookRequestDto(
        @Column(name = "title")
        @NotBlank(message = "Title cannot be blank")
        @Size(min = 3, max = 50)
        String title,

        @NotBlank(message = "Author cannot be blank")
        String author,

        @PositiveOrZero(message = "Price cannot be negative")
        double price,

        @PositiveOrZero(message = "Stock cannot be negative")
        int stock
) {}
