package com.project.bookstore_api.book;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.project.bookstore_api.book.dto.BookRequestDto;
import com.project.bookstore_api.book.dto.BookResponseDto;
import com.project.bookstore_api.book.mapper.BookMapperImp;
import com.project.bookstore_api.exception.BookNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapperImp bookMapperImp;

    public BookService(BookRepository bookRepository, BookMapperImp bookMapperImp) {
        this.bookRepository = bookRepository;
        this.bookMapperImp = bookMapperImp;
    }

    public Optional<BookResponseDto> getBook(Long id){
        return bookRepository.findById(id)
                             .map(bookMapperImp::toDto);
    }

    public List<BookResponseDto> getBooks(){
        return bookMapperImp.toDtoList(bookRepository.findAll());
    }

    public Page<BookResponseDto> getBooks(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAll(pageable).map(bookMapperImp::toDto);
    }

    public Long createBook(BookRequestDto bookRequestDto){
         Book book = bookMapperImp.toEntity(bookRequestDto);
         Book savedBook = bookRepository.save(book);
         return savedBook.getId();

    }

    public List<Long> createBooks(List<BookRequestDto> bookRequestDtos){
        List<Book> books = bookMapperImp.toEntityList(bookRequestDtos);
        List<Book> savedBook = bookRepository.saveAll(books);
        return savedBook.stream()
                .map(Book::getId)
                .collect(Collectors.toList());
    }

    public Long updateBookField(Long id, BookRequestDto bookRequestDto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        bookMapperImp.updateEntity(bookRequestDto, existingBook);

        bookRepository.save(existingBook);
        return existingBook.getId();
    }

    public Long updatePrice(Long id, double price) {
        BookRequestDto bookRequestDto = new BookRequestDto(null, null, price, 0);
        return updateBookField(id, bookRequestDto);
    }

    public Long updateStock(Long id, int stock) {
        BookRequestDto bookRequestDto = new BookRequestDto(null, null, 0, stock);
        return updateBookField(id, bookRequestDto);
    }

    public Long updateAuthor(Long id, String author) {
        BookRequestDto bookRequestDto = new BookRequestDto(null, author, 0, 0);
        return updateBookField(id, bookRequestDto);
    }

    public void deleteBook(Long id){
        if(bookRepository.existsById(id)){
            bookRepository.deleteById(id);
        }
        else{
            throw new BookNotFoundException(id);
        }
    }

    public boolean checkBookAvailability(Long id){
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return existingBook.getStock()>0;
    }
}
