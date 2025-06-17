package com.project.bookstore_api.features.basket.controller;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.bookstore_api.features.basket.dto.BasketResponse;
import com.project.bookstore_api.features.basket.service.BasketService;

@RestController
@RequestMapping("basket")
@Tag(name = "Basket")
@RequiredArgsConstructor
public class BasketControllerImpl implements BasketController {
    private final BasketService basketService;

    @Override
    @PostMapping("/add/{bookId}")
    @Operation(summary = "Add Books to Basket")
    public ResponseEntity<Void> addToBasket(@PathVariable Long bookId,
                                            @RequestParam(defaultValue = "1") int quantity) throws RuntimeException {
        basketService.addToBasket(bookId, quantity);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping
    @Operation(summary = "Get Basket Content")
    public ResponseEntity<BasketResponse> getBasket() {
        return ResponseEntity.ok(basketService.getBasket());
    }

    @Override
    @DeleteMapping("/{basketId}/clear")
    public ResponseEntity<Void> clearBasket(Long basketId) {
        basketService.clearBasket(basketId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{basketId}/clear/item/{basketItemId}")
    public ResponseEntity<Void> clearBasketItem(Long basketId, Long basketItemId) {
        basketService.clearBasketItem(basketItemId,basketItemId);
        return ResponseEntity.noContent().build();
    }
}
