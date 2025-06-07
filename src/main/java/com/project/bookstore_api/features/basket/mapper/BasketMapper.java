package com.project.bookstore_api.features.basket.mapper;

import com.project.bookstore_api.features.basket.dto.BasketResponse;
import com.project.bookstore_api.features.basket.model.Basket;

public interface BasketMapper {
    BasketResponse toDto(Basket basket);
    Basket toEntity(BasketResponse basketResponse);
}
