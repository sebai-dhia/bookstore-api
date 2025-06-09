package com.project.bookstore_api.features.order.service;

import java.util.List;

import com.project.bookstore_api.features.order.dto.OrderResponse;
import com.project.bookstore_api.features.order.model.CancelReason;
import com.project.bookstore_api.features.order.model.OrderStatus;


public interface OrderService {

    OrderResponse getOrderById(Long orderId);
    List<OrderResponse> getOrdersByCustomer(Long customerId);
    void cancelOrder(Long orderId, CancelReason reason);
    List<OrderResponse> listAll();
    void assignOrderStatus(Long orderId, OrderStatus status);
}
