package com.project.bookstore_api.book;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/page")
    public Page<Book> getBooksPaged(@RequestParam int page, @RequestParam int size) {
        return bookService.getBooks(page, size);
    }
    @GetMapping("/test")
    public String test() {
        return "Controller is working!";
    }


    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @PostMapping
    public Long createBook(@RequestBody Book book) {
        System.out.println("PASS: " + book.getAuthor());
        return bookService.createBook(book);
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
