package com.project.bookstore_api.features.order.mapper;

import com.project.bookstore_api.features.order.dto.OrderItemResponse;
import com.project.bookstore_api.features.order.model.OrderItem;

public interface OrderItemMapper {
        OrderItemResponse toDto(OrderItem item);
        OrderItem toEntity(OrderItemResponse item);
}
