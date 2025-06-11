package com.project.bookstore_api.features.payment.mapper;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.project.bookstore_api.features.order.OrderRepository;
import com.project.bookstore_api.features.order.model.Order;
import com.project.bookstore_api.features.payment.dto.PaymentResponse;
import com.project.bookstore_api.features.payment.model.Payment;


@Component
@RequiredArgsConstructor
public class PaymentMapperImpl  implements PaymentMapper{

    final private OrderRepository repository;
    @Override
    public PaymentResponse toDto(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getMethod(),
                payment.getStatus(),
                payment.getAmount(),
                payment.getOrder().getId(),
                payment.getMessageForStatus(payment.getStatus()),
                payment.getCreatedAt()
        );
    }

    @Override
    public Payment toEntity(PaymentResponse paymentResponse) {

        Order order = repository.findById(paymentResponse.orderReference())
                .orElseThrow(
                        ()->new RuntimeException("Order Not Found with ID "+paymentResponse.orderReference())
                );

        return new Payment(
                paymentResponse.paymentId(),
                paymentResponse.method(),
                paymentResponse.status(),
                paymentResponse.amount(),
                paymentResponse.timestamp(),
                null,
                order
        );
    }
}
