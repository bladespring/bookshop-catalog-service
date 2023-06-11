package com.bladespring.bookshop.catalogservice.domain;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import com.bladespring.bookshop.catalogservice.config.DataConfig;

@DataJpaTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("integration")
public class BookRepositoryTests {
    @Autowired
    private BookRepository bookRepository;

    @Test
    void findBookByIsbnWhenExisting() {
        var bookIsbn = "1234567897";
        var book = Book.of(bookIsbn, "Title", "Author", 12.50);
        bookRepository.save(book);
        Optional<Book> actualBook = bookRepository.findByIsbn(bookIsbn);
        assertThat(actualBook).isPresent();
        assertThat(actualBook.get().getIsbn()).isEqualTo(bookIsbn);
    }
}
