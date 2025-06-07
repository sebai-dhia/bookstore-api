package com.project.bookstore_api.features.basket.mapper;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.project.bookstore_api.features.basket.dto.BasketItemResponse;
import com.project.bookstore_api.features.basket.dto.BasketResponse;
import com.project.bookstore_api.features.basket.model.Basket;
import com.project.bookstore_api.features.basket.model.BasketItem;
import com.project.bookstore_api.features.user.repository.CustomerRepository;
import com.project.bookstore_api.features.user.model.Customer;

@Component
@RequiredArgsConstructor
public class BasketMapperImpl implements BasketMapper{
    private final BasketItemMapper mapper;
    private final CustomerRepository repository;

    @Override
    public BasketResponse toDto(Basket basket) {
        if (basket == null) {
            return null;
        }

        List<BasketItemResponse> itemResponses = basket.getItems() == null
                ? List.of()
                : basket.getItems().stream()
                .map(mapper::toDto)
                .toList();

        return new BasketResponse(
                basket.getId(),
                basket.getCustomer().getId(),
                itemResponses,
                basket.getDateCreated()
        );
    }

    @Override
    public Basket toEntity(BasketResponse basketResponse) {
        if (basketResponse == null) {
            return null;
        }

        Customer customer = repository.findById(basketResponse.customerId())
                .orElseThrow(
                        ()->new RuntimeException("Customer Not Found With id "+ basketResponse.customerId())
                );
        List<BasketItem> items = basketResponse.items() == null
                ? List.of()
                : basketResponse.items().stream()
                .map(mapper::toEntity)
                .toList();

        return Basket.builder()
                .id(basketResponse.basketId())
                .customer(customer)
                .items(items)
                .dateCreated(basketResponse.dateCreated())
                .build();
    }
}
