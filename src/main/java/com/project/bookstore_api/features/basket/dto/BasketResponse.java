package com.project.bookstore_api.features.basket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Basket Response Dto")
public record BasketResponse(
        Long basketId,
        @JsonIgnore Long customerId,
        List<BasketItemResponse> items,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime dateCreated
) {}
