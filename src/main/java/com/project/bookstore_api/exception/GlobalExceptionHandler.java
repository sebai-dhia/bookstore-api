package com.project.bookstore_api.exception;

import java.util.Map;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.project.bookstore_api.features.book.exception.BookNotFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFound(BookNotFoundException exception, WebRequest request){
        logger.error("BOOK NOT FOUND: {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.BOOK_NOT_FOUND)
                .toBuilder()
                .message(exception.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();


        return ResponseEntity.status(ErrorCode.BOOK_NOT_FOUND.getHttpStatus())
                .body(errorResponse);


    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handelAllException(Exception exception, WebRequest request){

        logger.error("INTERNAL SERVER ERROR: {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_ERROR)
                .toBuilder()
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return ResponseEntity.status(ErrorCode.INTERNAL_ERROR.getHttpStatus()).body(errorResponse);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception, WebRequest request){

        Map<String, String> errors = exception.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "Invalid Value"
                ));

        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.VALIDATION_ERROR, errors)
                .toBuilder()
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return  ResponseEntity.status(ErrorCode.VALIDATION_ERROR.getHttpStatus()).body(errorResponse);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        v -> v.getPropertyPath().toString().split("\\.")[0],
                        ConstraintViolation::getMessage
                ));

        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.VALIDATION_ERROR, errors)
                .toBuilder()
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
