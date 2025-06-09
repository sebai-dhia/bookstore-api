package com.project.bookstore_api.features.order.model;

public enum CancelReason {
    CUSTOMER_REQUEST,           // Customer changed mind or canceled manually
    PAYMENT_NOT_COLLECTED,      // Cash not received on delivery or at pickup
    OUT_OF_STOCK,               // Book is no longer available
    FRAUD_SUSPECTED,            // Suspicious activity detected
    PICKUP_EXPIRED,             // Customer didn't pick up within the allowed window
    DELIVERY_FAILED             // Could not deliver (e.g., customer unreachable)
}

