package com.project.bookstore_api.features.basket.service;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.bookstore_api.features.basket.BasketRepository;
import com.project.bookstore_api.features.basket.dto.BasketResponse;
import com.project.bookstore_api.features.basket.mapper.BasketMapper;
import com.project.bookstore_api.features.basket.model.BasketItem;
import com.project.bookstore_api.features.basket.model.Basket;
import com.project.bookstore_api.features.book.Book;
import com.project.bookstore_api.features.book.BookRepository;
import com.project.bookstore_api.features.book.exception.BookNotFoundException;
import com.project.bookstore_api.features.book.exception.OutOfStockException;
import com.project.bookstore_api.features.user.repository.GuestRepository;
import com.project.bookstore_api.features.user.model.Customer;
import com.project.bookstore_api.features.user.model.Guest;

import com.project.bookstore_api.security.CustomUserDetails;
import com.project.bookstore_api.security.SecurityUtils;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService{

    final private BookRepository bookRepository;
    final private BasketRepository basketRepository;
    final private GuestRepository guestRepository;
    final private BasketMapper mapper;


    @Transactional
    @Override
    public void addToBasket(Long bookId, int quantity) {
        final CustomUserDetails currentUser = SecurityUtils.getCurrentUser();

        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        if (existingBook.getStock() - quantity < 0) {
            throw new OutOfStockException(bookId);
        }

        Customer customer = (Customer) currentUser.user(); // Guest or Member

        Basket basket;

        if (customer.getId() != null) {
            // Persistent customer: fetch basket or create
            basket = basketRepository.findByCustomerId(customer.getId())
                    .orElseGet(() -> Basket.builder()
                            .customer(customer)
                            .dateCreated(LocalDateTime.now())
                            .build());
        } else {
            // New guest: create new basket
            basket = Basket.builder()
                    .customer(customer)
                    .dateCreated(LocalDateTime.now())
                    .build();
        }

        // Add or update item in basket
        basket.getItems().stream()
                .filter(item -> item.getBook().getId().equals(bookId))
                .findFirst()
                .ifPresentOrElse(item -> {
                    item.setQuantity(item.getQuantity() + quantity);
                    item.setPriceAtPurchase(item.getPriceAtPurchase() + existingBook.getPrice());
                }, () -> {
                    BasketItem newItem = BasketItem.builder()
                            .book(existingBook)
                            .quantity(quantity)
                            .priceAtPurchase(existingBook.getPrice())
                            .build();
                    basket.addItem(newItem);
                });

        // Persist guest if needed
        if (customer instanceof Guest guest && guest.getId() == null) {
            guestRepository.save(guest); // assign ID
        }

        basketRepository.save(basket);
    }


    @Override
    public BasketResponse getBasket() {
        final CustomUserDetails currentUser = SecurityUtils.getCurrentUser();
        var basket = basketRepository.findByCustomerId(currentUser.user().getId())
                .orElseThrow(()->
                        new RuntimeException("There is no Basket associated with userId: "+currentUser.user().getId()));
        return mapper.toDto(basket);
    }

    @Transactional
    @Override
    public void clearBasket(Long basketId) {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(()->new RuntimeException("Basket Not found with id :"+ basketId));
        basket.getItems().clear();
    }

    @Transactional
    @Override
    public void clearBasketItem(Long basketId, Long basketItemId) {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(()->new RuntimeException("Basket Not found with id :"+ basketId));

        boolean removed = basket.getItems().removeIf(item ->item.getId().equals(basketItemId));

        if(!removed){
            throw new RuntimeException("Basket Item not found with Id "+basketItemId);
        }
    }
}
