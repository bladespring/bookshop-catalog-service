package com.bladespring.bookshop.catalogservice.web;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bladespring.bookshop.catalogservice.domain.Book;
import com.bladespring.bookshop.catalogservice.domain.BookService;
import com.bladespring.bookshop.catalogservice.exception.BookAlreadyExistsException;
import com.bladespring.bookshop.catalogservice.exception.BookNotFoundException;

@WebMvcTest(BookController.class)
public class BookControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void bookDetailReturns404WhenBookIsNotFound() throws Exception {
        String isbn = "0987654321";
        BDDMockito.given(bookService.viewBookDetails(isbn))
                .willThrow(BookNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + isbn))
                .andExpectAll(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void createBookReturns400WhenBookIsbnAlreadyExists() throws Exception {
        var book = Book.of("1234567890", "Title", "Author", 12.0);
        BDDMockito.given(bookService.addBookToCatalog(book))
                .willThrow(BookAlreadyExistsException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/books"))
                .andExpectAll(MockMvcResultMatchers.status().isBadRequest());
    }
}
