package com.project.bookstore_api.features.basket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;

public record BasketResponse(
        Long basketId,
        @JsonIgnore Long customerId,
        List<BasketItemResponse> items,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime dateCreated
) {}
