package com.project.bookstore_api.book;

import java.util.Optional;

import com.project.bookstore_api.features.book.Book;
import com.project.bookstore_api.features.book.BookRepository;
import com.project.bookstore_api.features.book.mapper.BookMapper;
import com.project.bookstore_api.features.book.service.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.bookstore_api.features.book.dto.BookResponseDto;
import com.project.bookstore_api.features.book.exception.BookNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    BookMapper bookMapper;

    @InjectMocks
    BookServiceImpl bookService;

    @Test
    void getBookById_ShouldReturnBook_WhenExists(){
        // Arrange
        Book book = new Book(1L,"clean Code", "Robert Cecil Martin", 100, 200);
        BookResponseDto bookResponseDto =   new BookResponseDto(
                1L,"clean Code", "Robert Cecil Martin", 100, 200);

        when(bookMapper.toDto(book)).thenReturn(bookResponseDto);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act
        BookResponseDto result = bookService.getBook(1L);

        // Assert
        assertAll(
                ()-> assertEquals("Robert Cecil Martin", result.author()),
                ()-> assertEquals(100, result.price()),
                ()-> assertEquals(200, result.stock())
        );
        verify(bookRepository).findById(1L);
    }

    @Test
    void getBookById_ShouldThrow_WhenNotFound(){
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert
        assertThrows(BookNotFoundException.class, ()->bookService.getBook(1L));

    }

    @Test
    void updatePrice_shouldUpdatePriceWhenBookExists() {
        // Arrange
        Long id = 1L;
        double newPrice = 29.99;
        Book existingBook = new Book();
        existingBook.setId(id);

        when(bookRepository.findById(id)).thenReturn(Optional.of(existingBook));

        // Act
        bookService.updatePrice(id, newPrice);

        // Assert
        verify(bookRepository).findById(id);

        // Simpler verification using argThat
        verify(bookMapper).updateEntity(
                argThat(dto ->
                        dto.title() == null &&
                        dto.author() == null &&
                        dto.price() == newPrice &&
                        dto.stock() == 0
                ),
                eq(existingBook)
        );

        verify(bookRepository).save(existingBook);
    }

    @Test
    void updatePrice_shouldThrowExceptionWhenBookNotFound() {
        // Arrange
        Long id = 1L;
        double newPrice = 29.99;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BookNotFoundException.class, () -> {
            bookService.updatePrice(id, newPrice);
        });

        verify(bookRepository).findById(id);
        verifyNoInteractions(bookMapper);
        verify(bookRepository, never()).save(any());
    }

}
