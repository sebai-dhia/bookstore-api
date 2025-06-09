package com.project.bookstore_api.features.order.model;

public enum OrderStatus {
    AWAITING_PAYMENT,
    PROCESSING, // Admin preparing goods
    SHIPPED,    // Cash: Dispatched (payment collected later)
    DELIVERED,  // Cash: Goods + payment received
    PAID,
    CANCELLED
}
