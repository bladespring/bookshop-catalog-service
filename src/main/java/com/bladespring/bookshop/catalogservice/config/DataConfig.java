package com.bladespring.bookshop.catalogservice.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.bladespring.bookshop.catalogservice.domain.Book;
import com.bladespring.bookshop.catalogservice.domain.BookRepository;

@Configuration
@EnableJpaAuditing
public class DataConfig {
    @Bean
    @Profile("testdata")
    public CommandLineRunner bookDataLoader(BookRepository bookRepository) {
        return args -> {
            bookRepository.deleteAll();
            var book1 = Book.of("1234567891", "Northern Lights", "Lyra Silverstar", 9.90);
            var book2 = Book.of("1234567892", "Polar Journey", "Iorek Polarson", 12.90);
            bookRepository.saveAll(List.of(book1, book2));
        };
    }
}
