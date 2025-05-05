package com.project.bookstore_api.book;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.project.bookstore_api.book.dto.BookRequestDto;
import com.project.bookstore_api.book.dto.BookResponseDto;


@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookResponseDto> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/page")
    public Page<BookResponseDto> getBooksPaged(@RequestParam int page, @RequestParam int size) {
        return bookService.getBooks(page, size);
    }

    @GetMapping("/{id}")
    public BookResponseDto getBookById(@PathVariable Long id) {
        return bookService.getBook(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }

    @PostMapping
    public Long createBook(@Valid @RequestBody BookRequestDto bookRequestDto) {
        System.out.println("PASS: " + bookRequestDto.author());
        return bookService.createBook(bookRequestDto);
    }

    @PostMapping("/batch")
    public List<Long> createBooks(@Valid @RequestBody List<BookRequestDto> bookRequestDtos) {
        return bookService.createBooks(bookRequestDtos);
    }

    @PatchMapping("/{id}/price")
    public Long updatePrice(@PathVariable Long id, @RequestParam double price) {
        return bookService.updatePrice(id, price);
    }

    @PatchMapping("/{id}/stock")
    public Long updateStock(@PathVariable Long id, @RequestParam int stock) {
        return bookService.updateStock(id, stock);
    }

    @PatchMapping("/{id}/author")
    public Long updateAuthor(@PathVariable Long id, @RequestParam String author) {
        return bookService.updateAuthor(id, author);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/{id}/available")
    public boolean checkAvailability(@PathVariable Long id) {
        return bookService.checkBookAvailability(id);
    }
}
