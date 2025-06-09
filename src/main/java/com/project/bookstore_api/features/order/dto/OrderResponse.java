package com.project.bookstore_api.features.order.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.project.bookstore_api.features.order.model.CancelReason;
import com.project.bookstore_api.features.order.model.OrderStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderResponse(
        Long orderId,
        @JsonIgnore Long customerId,
        List<OrderItemResponse> items,
        OrderStatus status,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime updatedAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime shippedAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime cancelledAt,
        CancelReason cancellationReason,
        String cancelledBy
) {}
