package com.project.bookstore_api.features.basket.mapper;

import com.project.bookstore_api.features.basket.dto.BasketItemResponse;
import com.project.bookstore_api.features.basket.model.BasketItem;

public interface BasketItemMapper {
    BasketItemResponse toDto(BasketItem item);
    BasketItem toEntity(BasketItemResponse item);
}
