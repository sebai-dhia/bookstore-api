package com.project.bookstore_api.features.book.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.project.bookstore_api.features.book.Book;
import com.project.bookstore_api.features.book.BookRepository;
import com.project.bookstore_api.features.book.mapper.BookMapper;
import com.project.bookstore_api.features.book.dto.BookRequestDto;
import com.project.bookstore_api.features.book.dto.BookResponseDto;
import com.project.bookstore_api.features.book.exception.BookNotFoundException;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookResponseDto getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookResponseDto> getBooks() {
        return bookMapper.toDtoList(bookRepository.findAll());
    }

    @Override
    public Page<BookResponseDto> getBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAll(pageable)
                .map(bookMapper::toDto);
    }

    @Override
    public Long createBook(BookRequestDto bookRequestDto) {
        Book book = bookMapper.toEntity(bookRequestDto);
        Book savedBook = bookRepository.save(book);
        return savedBook.getId();
    }

    @Override
    public List<Long> createBooks(List<BookRequestDto> bookRequestDtos) {
        List<Book> books = bookMapper.toEntityList(bookRequestDtos);
        List<Book> savedBooks = bookRepository.saveAll(books);
        return savedBooks.stream()
                .map(Book::getId)
                .collect(Collectors.toList());
    }

    public void updateBookField(Long id, BookRequestDto bookRequestDto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        bookMapper.updateEntity(bookRequestDto, existingBook);
        bookRepository.save(existingBook);
    }

    @Override
    public void updatePrice(Long id, double price) {
        BookRequestDto bookRequestDto = new BookRequestDto(null, null, price, 0);
        updateBookField(id, bookRequestDto);
    }

    @Override
    public void updateStock(Long id, int stock) {
        BookRequestDto bookRequestDto = new BookRequestDto(null, null, 0, stock);
        updateBookField(id, bookRequestDto);
    }

    @Override
    public void updateAuthor(Long id, String author) {
        BookRequestDto bookRequestDto = new BookRequestDto(null, author, 0, 0);
        updateBookField(id, bookRequestDto);
    }

    @Override
    public void updateTitle(Long id, String title) {
        BookRequestDto bookRequestDto = new BookRequestDto(title, null, 0, 0);
        updateBookField(id, bookRequestDto);
    }

    @Override
    public void deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new BookNotFoundException(id);
        }
    }

    @Override
    public boolean isInStock(Long id) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return existingBook.getStock() > 0;
    }
}
