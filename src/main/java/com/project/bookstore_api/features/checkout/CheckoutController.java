package com.project.bookstore_api.features.checkout;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstore_api.features.checkout.dto.CheckoutResponse;


@RestController
@RequestMapping("checkout")
@Tag(name = "Checkout")
@RequiredArgsConstructor
public class CheckoutController {
    final private CheckoutService checkoutService;

    @PostMapping("/{customerId}")
    @PreAuthorize("hasAnyRole('MEMBER', 'GUEST')")
    ResponseEntity<CheckoutResponse> checkout(@PathVariable Long customerId){
        return ResponseEntity.ok(checkoutService.checkout(customerId));
    }
}
