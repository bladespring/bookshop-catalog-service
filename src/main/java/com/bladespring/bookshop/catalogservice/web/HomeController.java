package com.bladespring.bookshop.catalogservice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bladespring.bookshop.catalogservice.config.BookshopProperties;

@RestController
public class HomeController {
    private final BookshopProperties bookshopProperties;

    @Autowired
    public HomeController(BookshopProperties properties) {
        bookshopProperties = properties;
    }

    @GetMapping("/")
    public String getGreeting() {
        return bookshopProperties.getGreeting();
    }
}
