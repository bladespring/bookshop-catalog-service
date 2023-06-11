package com.bladespring.bookshop.catalogservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "bookshop")
@Getter
@Setter
public class BookshopProperties {
    /*
     * A message to welcome users
     */
    private String greeting;
}
