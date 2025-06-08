package com.project.bookstore_api.features.book.service;

import java.util.List;

import com.project.bookstore_api.features.book.dto.BookRequestDto;
import com.project.bookstore_api.features.book.dto.BookResponseDto;

import org.springframework.data.domain.Page;

public interface BookService {
    BookResponseDto getBook(Long id);
    List<BookResponseDto> getBooks();
    Page<BookResponseDto> getBooks(int page, int size);
    Long createBook(BookRequestDto bookRequestDto);
    List<Long> createBooks(List<BookRequestDto> bookRequestDtos);
    void updatePrice(Long id, double price);
    void updateStock(Long id, int stock);
    void updateAuthor(Long id, String author);
    void updateTitle(Long id, String title);
    void deleteBook(Long id);
    boolean isInStock(Long id);
}
