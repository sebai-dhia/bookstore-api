package com.project.bookstore_api.features.order.controller;

import java.util.List;

import com.project.bookstore_api.features.order.dto.OrderResponse;
import com.project.bookstore_api.features.order.model.CancelReason;
import com.project.bookstore_api.features.order.model.OrderStatus;
import org.springframework.http.ResponseEntity;

public interface OrderController {
    ResponseEntity<OrderResponse> getOrderById(Long orderId);
    ResponseEntity<List<OrderResponse>> getOrdersByCustomer(Long customerId);
    void cancelOrder(Long orderId, CancelReason reason);
    ResponseEntity<List<OrderResponse>> listAll();
    void assignOrderStatus(Long orderId, OrderStatus status);
}
