package com.project.bookstore_api.features.order.mapper;

import com.project.bookstore_api.features.order.dto.OrderResponse;
import com.project.bookstore_api.features.order.model.Order;

public interface OrderMapper {
        OrderResponse toDto(Order order);
        Order toEntity(OrderResponse basketResponse);
}
