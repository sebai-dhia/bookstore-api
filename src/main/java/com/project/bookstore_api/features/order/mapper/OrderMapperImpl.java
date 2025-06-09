package com.project.bookstore_api.features.order.mapper;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.project.bookstore_api.features.order.dto.OrderItemResponse;
import com.project.bookstore_api.features.order.dto.OrderResponse;
import com.project.bookstore_api.features.order.model.Order;
import com.project.bookstore_api.features.order.model.OrderItem;
import com.project.bookstore_api.features.user.repository.CustomerRepository;
import com.project.bookstore_api.features.user.model.Customer;

@Component
@RequiredArgsConstructor
public class OrderMapperImpl implements OrderMapper{
    final private OrderItemMapper mapper;
    final private CustomerRepository repository;

    @Override
    public OrderResponse toDto(Order order) {
        if (order == null) {
            return null;
        }

        List<OrderItemResponse> items = order.getItems() == null
                ? List.of()
                : order.getItems().stream()
                .map(mapper::toDto)
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getCustomer() != null ? order.getCustomer().getId() : null,
                items,
                order.getStatus(),
                order.getDateCreated(),
                order.getDateUpdated(),
                order.getDateShipped(),
                order.getCancelledAt(),
                order.getCancellationReason(),
                order.getCancelledBy()
        );
    }


    @Override
    public Order toEntity(OrderResponse order) {
        if(order == null){
            return null;
        }

        Customer customer = repository.findById(order.customerId())
                .orElseThrow(
                        ()->new RuntimeException("Customer Not Found With id "+ order.customerId())
                );

        List<OrderItem> items = order.items() == null
                ? List.of()
                : order.items().stream()
                .map(mapper::toEntity)
                .toList();

        return Order.builder()
                .id(order.orderId())
                .customer(customer)
                .items(items)
                .status(order.status())
                .dateCreated(order.createdAt())
                .dateUpdated(order.updatedAt())
                .dateShipped(order.shippedAt())
                .cancelledAt(order.cancelledAt())
                .cancellationReason(order.cancellationReason())
                .cancelledBy(order.cancelledBy())
                .build();
    }
}
