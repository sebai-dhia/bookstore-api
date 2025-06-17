package com.project.bookstore_api.features.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Book Response Dto")
public record BookResponseDto(
        @Schema(description = "The unique identifier of the book", example = "1")
        Long id,
        @Schema(description = "Title of the book", example = "Effective Java", requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
        @Schema(description = "Author name", example = "Joshua Bloch")
        String author,
        @Schema(example = "29.99")
        double price,
        @JsonIgnore int stock
) {}
