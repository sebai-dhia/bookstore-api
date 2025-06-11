package com.project.bookstore_api.features.checkout;

import com.project.bookstore_api.features.checkout.dto.CheckoutResponse;

public interface CheckoutService {
    CheckoutResponse checkout(Long customerId);
}
