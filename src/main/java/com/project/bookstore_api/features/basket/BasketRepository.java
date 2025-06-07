package com.project.bookstore_api.features.basket;

import java.util.Optional;

import com.project.bookstore_api.features.basket.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository  extends JpaRepository<Basket, Long> {
    Optional<Basket> findByCustomerId(Long customerId);
}
