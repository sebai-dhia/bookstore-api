package com.project.bookstore_api.features.payment.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;

import com.project.bookstore_api.features.order.model.Order;


@Builder
@Getter
@Setter
@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false)
    private double amount;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;


    public String getMessageForStatus(PaymentStatus status) {
        return switch (status) {
            case COMPLETED -> "The payment was completed successfully.";
            case PENDING   -> "The payment is currently pending.";
            case FAILED    -> "The payment failed. Please try again.";
            case REFUNDED  -> "The payment was refunded.";
        };

    }

}
