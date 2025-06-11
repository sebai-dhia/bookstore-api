package com.project.bookstore_api.features.book.exception;

import lombok.Getter;

@Getter
public class OutOfStockException extends RuntimeException {
    private final Long bookId;
    private final String bookTitle;
    private final double availableStock;
    private final double requestedQuantity;

    public OutOfStockException(Long bookId) {
        this(bookId, null, 0, 0,
                "Book with ID " + bookId + " is out of stock");
    }

    public OutOfStockException(Long bookId, String bookTitle, double availableStock, double requestedQuantity){
        this(bookId, bookTitle, availableStock, requestedQuantity,
                String.format("Insufficient stock for book '%s' (ID: %d). " +
                                "Available: %.2f, Requested: %.2f",
                        bookTitle, bookId, availableStock, requestedQuantity));
    }

    public OutOfStockException(Long bookId, String bookTitle, double availableStock, double requestedQuantity,String message) {
        super(message);
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.availableStock = availableStock;
        this.requestedQuantity = requestedQuantity;
    }
}
