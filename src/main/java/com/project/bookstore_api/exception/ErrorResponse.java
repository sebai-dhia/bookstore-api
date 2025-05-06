package com.project.bookstore_api.exception;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonInclude;


@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String code;
    private String message;
    private String path;
    private Map<String, String> details;

    public static ErrorResponse of(ErrorCode errorCode){
        return ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build();
    }

    public static ErrorResponse of(ErrorCode errorCode, Map<String, String> details){
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .details(Optional.ofNullable(details).orElse(Collections.emptyMap()))
                .build();
    }
}
