package com.project.bookstore_api.book;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;



    @Test
    void getBook_ShouldReturn200_WhenExists() throws Exception{
        // Arrange
        Book book =  new Book(null,"Clean Code", "Robert Cecil Martin", 100, 200);
        bookRepository.save(book);
        System.out.println(book.getId());

        // Act & Assert
        mockMvc.perform(get("/api/books/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    void getBook_ShouldReturn400_WhenNotExists()throws Exception{
        mockMvc.perform(get("/api/books/100"))
                .andExpect(status().isNotFound());
    }
}
