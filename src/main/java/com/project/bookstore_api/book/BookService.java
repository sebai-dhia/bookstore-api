package com.project.bookstore_api.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    public Page<Book> getBooks(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAll(pageable);
    }

    public Long createBook(Book book){
         bookRepository.save(book);
         return book.getId();

    }

    public Optional<Book> getBook(Long id){
        return bookRepository.findById(id);

    }

    public Long updateBookField(Long id, Consumer<Book> updateField) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        updateField.accept(existingBook);

        bookRepository.save(existingBook);
        return existingBook.getId();
    }

    public Long updatePrice(Long id, double price) {
        return updateBookField(id, book -> book.setPrice(price));
    }

    public Long updateStock(Long id, int stock) {
        return updateBookField(id, book -> book.setStock(stock));
    }

    public Long updateAuthor(Long id, String author) {
        return updateBookField(id, book -> book.setAuthor(author));
    }

    public void deleteBook(Long id){
        if(bookRepository.existsById(id)){
            bookRepository.deleteById(id);
        }
        else{
            throw new RuntimeException(("Book "+ id +" not Found"));
        }
    }

    public boolean checkBookAvailability(Long id){
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return existingBook.getStock()>0;
    }
}
