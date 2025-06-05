package com.project.bookstore_api.features.user.model;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import com.project.bookstore_api.features.basket.model.Basket;
import com.project.bookstore_api.features.order.model.Order;
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
public abstract class Customer extends User {
    private String address;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Basket basket;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    protected Customer (Role role){
        super(role);
    }
}
