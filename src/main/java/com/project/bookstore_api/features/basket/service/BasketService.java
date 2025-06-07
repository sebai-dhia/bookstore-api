package com.project.bookstore_api.features.basket.service;

import com.project.bookstore_api.features.basket.dto.BasketResponse;

public interface BasketService {
    void addToBasket(Long bookId, int quantity) throws RuntimeException; // OutOfStockException, BookNotFoundException
    BasketResponse getBasket();
    void clearBasket(Long basketId);
    void clearBasketItem(Long basketId, Long basketItemId);
}
