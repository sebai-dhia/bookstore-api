package com.project.bookstore_api.features.order.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.*;

import com.project.bookstore_api.features.book.Book;

@Builder
@Getter
@Setter
@Entity
@Table(name = "order_item")
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Positive(message = "quantity cannot be negative")
    private int quantity;

    @PositiveOrZero(message = "price cannot be negative")
    private double priceAtPurchase;


}
