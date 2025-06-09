package com.project.bookstore_api.features.order.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.project.bookstore_api.features.order.dto.OrderResponse;
import com.project.bookstore_api.features.order.model.CancelReason;
import com.project.bookstore_api.features.order.model.OrderStatus;
import com.project.bookstore_api.features.order.service.OrderService;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController{
    private final OrderService service;

    @Override
    @GetMapping("$/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable  Long orderId) {
        return ResponseEntity.ok(service.getOrderById(orderId)) ;
    }

    @Override
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("#customerId == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponse>> getOrdersByCustomer( @PathVariable Long customerId) {
        return ResponseEntity.ok(service.getOrdersByCustomer(customerId));
    }

    @Override
    @PatchMapping("/{orderId}/cancel")
    public void cancelOrder(@PathVariable Long orderId, @RequestParam CancelReason reason) {
        service.cancelOrder(orderId, reason);
        ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponse>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Override
    @PatchMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public void assignOrderStatus( @PathVariable Long orderId, @RequestParam OrderStatus status) {
        service.assignOrderStatus(orderId, status);
        ResponseEntity.noContent().build();
    }
}
