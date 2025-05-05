package com.project.bookstore_api.book.mapper;

import java.util.List;

import org.springframework.stereotype.Component;


import com.project.bookstore_api.book.Book;
import com.project.bookstore_api.book.dto.BookRequestDto;
import com.project.bookstore_api.book.dto.BookResponseDto;

@Component
public class BookMapperImp implements BookMapper{
    @Override
    public BookResponseDto toDto(Book book) {
        return new BookResponseDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getStock());
    }

    @Override
    public Book toEntity(BookRequestDto bookRequestDto) {
        return new Book(
                null,
                bookRequestDto.title(),
                bookRequestDto.author(),
                bookRequestDto.price(),
                bookRequestDto.stock()
        );
    }

    @Override
    public void updateEntity(BookRequestDto bookRequestDto, Book book) {
        if (bookRequestDto.title() != null && !bookRequestDto.title().isBlank()) {
            book.setTitle(bookRequestDto.title());
        }

        if (bookRequestDto.author() != null && !bookRequestDto.author().isBlank()) {
            book.setAuthor(bookRequestDto.author());
        }

        if (bookRequestDto.price() != 0.0) {
            book.setPrice(bookRequestDto.price());
        }

        if (bookRequestDto.stock() != 0) {
            book.setStock(bookRequestDto.stock());
        }
    }

    @Override
    public List<BookResponseDto> toDtoList(List<Book> books) {
        return books.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<Book> toEntityList(List<BookRequestDto> bookRequestDtos) {
        return bookRequestDtos.stream()
                .map(this::toEntity)
                .toList();
    }
}
