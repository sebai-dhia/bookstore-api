package com.project.bookstore_api.features.basket.controller;

import com.project.bookstore_api.features.basket.dto.BasketResponse;
import org.springframework.http.ResponseEntity;

public interface BasketController {
    ResponseEntity<Void> addToBasket(Long bookId, int quantity) throws RuntimeException; // OutOfStockException, BookNotFoundException
    ResponseEntity<BasketResponse> getBasket();
    ResponseEntity<Void> clearBasket(Long basketId);
    ResponseEntity<Void> clearBasketItem(Long basketId, Long basketItemId);
}
