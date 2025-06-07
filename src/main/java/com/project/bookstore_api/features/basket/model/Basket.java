package com.project.bookstore_api.features.basket.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

import com.project.bookstore_api.features.user.model.Customer;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_created", nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
    @Builder.Default
    private List<BasketItem> items = new ArrayList<>();

    @OneToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public void addItem(BasketItem item) {
        item.setBasket(this);
        this.items.add(item);
    }
    public void removeItem(BasketItem item){
        items.remove(item);
        item.setBasket(null);
    }
    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(item -> item.getPriceAtPurchase() * item.getQuantity())
                .sum();
    }

}
