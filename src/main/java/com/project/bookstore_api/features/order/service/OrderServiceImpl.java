package com.project.bookstore_api.features.order.service;

import java.time.LocalDateTime;
import java.util.List;

import com.project.bookstore_api.features.order.OrderRepository;
import com.project.bookstore_api.features.order.dto.OrderResponse;
import com.project.bookstore_api.features.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.bookstore_api.features.order.model.CancelReason;
import com.project.bookstore_api.features.order.model.Order;
import com.project.bookstore_api.features.order.model.OrderStatus;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    final private OrderRepository orderRepository;
    final private OrderMapper mapper;

    @Override
    public OrderResponse getOrderById(Long orderId) {
         Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("There is no Order with id :"+ orderId));
         return mapper.toDto(order);
    }

    @Override
    public List<OrderResponse> getOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void cancelOrder(Long orderId, CancelReason reason) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order not found with Id: "+orderId));
        order.setStatus(OrderStatus.CANCELLED);
        order.setCancelledAt(LocalDateTime.now());
        order.setCancellationReason(reason);
    }

    @Override
    public List<OrderResponse> listAll() {
        return orderRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void assignOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order not found with Id: "+orderId));
        order.setStatus(status);
        order.setDateUpdated(LocalDateTime.now());
    }
}
