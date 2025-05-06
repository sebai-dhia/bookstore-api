package com.project.bookstore_api.book;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<BookResponseDto>> getBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<BookResponseDto>> getBooksPaged(
            @RequestParam @Min(1) int page,
            @RequestParam @Min(1) @Max(100) int size) {
        return ResponseEntity.ok(bookService.getBooks(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @PostMapping
    public ResponseEntity<Long> createBook(@Valid @RequestBody BookRequestDto bookRequestDto) {
        System.out.println("PASS: " + bookRequestDto.author());
        return ResponseEntity.ok(bookService.createBook(bookRequestDto));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Long>> createBooks(@Valid @RequestBody List<BookRequestDto> bookRequestDtos) {
        return ResponseEntity.ok(bookService.createBooks(bookRequestDtos));
    }

    @PatchMapping("/{id}/price")
    public ResponseEntity<Long> updatePrice(@PathVariable Long id, @RequestParam double price) {
        return ResponseEntity.ok(bookService.updatePrice(id, price));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Long> updateStock(@PathVariable Long id, @RequestParam int stock) {
        return ResponseEntity.ok(bookService.updateStock(id, stock));
    }

    @PatchMapping("/{id}/author")
    public ResponseEntity<Long> updateAuthor(@PathVariable Long id, @RequestParam String author) {
        return ResponseEntity.ok(bookService.updateAuthor(id, author));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/available")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.checkBookAvailability(id));
    }
}
