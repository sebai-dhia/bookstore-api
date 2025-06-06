package com.project.bookstore_api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    BOOK_NOT_FOUND("BOOK_404", "The requested book not found", HttpStatus.NOT_FOUND),

    VALIDATION_ERROR("VAL_400", "Validation failed", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR("GEN_500", "An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);


    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

}
