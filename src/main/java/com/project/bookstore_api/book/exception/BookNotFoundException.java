package com.project.bookstore_api.book.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long bookId) {
        super("Book with Id " + bookId + " not Found.");
    }
    public BookNotFoundException(String message) {
        super(message);
    }
}
