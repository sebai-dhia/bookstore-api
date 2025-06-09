package com.project.bookstore_api.features.order.mapper;

import org.springframework.stereotype.Component;

import com.project.bookstore_api.features.book.Book;
import com.project.bookstore_api.features.book.dto.BookResponseDto;
import com.project.bookstore_api.features.order.dto.OrderItemResponse;
import com.project.bookstore_api.features.order.model.OrderItem;

@Component
public class OrderItemMapperImpl implements OrderItemMapper{
    @Override
    public OrderItemResponse toDto(OrderItem item) {
        return new OrderItemResponse(
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
    public OrderItem toEntity(OrderItemResponse item) {
        if(item == null){
            return null;
        }
        Book book = Book.builder()
                .id(item.bookResponseDto().id())
                .author(item.bookResponseDto().author())
                .price(item.bookResponseDto().price())
                .stock(item.bookResponseDto().stock())
                .build();

        return OrderItem.builder()
                .book(book)
                .quantity(item.quantity())
                .priceAtPurchase(item.priceAtPurchase())
                .build();
    }
}
