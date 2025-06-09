package com.project.bookstore_api.features.order.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import lombok.*;

import com.project.bookstore_api.features.user.model.Customer;

@Builder
@Getter
@Setter
@Entity
@Table(name = "\"order\"")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_created", nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;

    @Column(name = "date_shipped")
    private LocalDateTime dateShipped;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "cancellation_reason")
    private CancelReason cancellationReason;

    @Column(name = "cancelled_by") // User ID or "system"
    private String cancelledBy;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    public void addItem(OrderItem item){
        items.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderItem item){
        items.remove(item);
        item.setOrder(null);
    }
    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(item -> item.getPriceAtPurchase() * item.getQuantity())
                .sum();
    }

}
