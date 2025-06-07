package com.project.bookstore_api.features.basket.mapper;

import com.project.bookstore_api.features.basket.dto.BasketItemResponse;
import com.project.bookstore_api.features.basket.model.BasketItem;
import com.project.bookstore_api.features.book.Book;
import com.project.bookstore_api.features.book.dto.BookResponseDto;
import org.springframework.stereotype.Component;

@Component
public class BasketItemMapperImpl implements BasketItemMapper{
    @Override
    public BasketItemResponse toDto(BasketItem item) {
        return new BasketItemResponse(
                new BookResponseDto(
                        item.getBook().getId(),
                        item.getBook().getTitle(),
                        item.getBook().getAuthor(),
                        item.getBook().getPrice(),
                        item.getBook().getStock()
                ),
                item.getQuantity(),
                item.getPriceAtPurchase()
        );
    }

    @Override
    public BasketItem toEntity(BasketItemResponse item) {
        if (item == null) {
            return null;
        }

        Book book = Book.builder()
                .id(item.bookResponseDto().id())
                .title(item.bookResponseDto().title())
                .author(item.bookResponseDto().author())
                .price(item.bookResponseDto().price())
                .stock(item.bookResponseDto().stock())
                .build();

        return BasketItem.builder()
                .book(book)
                .quantity(item.quantity())
                .priceAtPurchase(item.priceAtPurchase())
                .build();
    }
}
