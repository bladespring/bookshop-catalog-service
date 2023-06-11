package com.bladespring.bookshop.catalogservice.domain;

import java.util.stream.Collectors;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BookValidationTests {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validFieldsPassValidation() {
        var book = Book.of("1234567891", "Title", "Author", 10.0);
        var violations = validator.validate(book);
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    void blankIsbnFailsValidation() {
        var book = Book.of("", "Title", "Author", 10.0);
        var violations = validator.validate(book);
        Assertions.assertThat(violations.stream().map(v -> v.getMessage()).collect(Collectors.toList()))
                .contains("The book ISBN must be defined.");
    }

    @Test
    void invalidIsbnFailsValidation() {
        var book = Book.of("a1234567891", "Title", "Author", 10.0);
        var violations = validator.validate(book);
        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The ISBN format must be valid.");
    }

    @Test
    void blankAuthorFailsValidation() {
        var book = Book.of("1234567891", "Title", "", 10.0);
        var violations = validator.validate(book);
        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The book author must be defined.");
    }

    @Test
    void blankTitleFailsValidation() {
        var book = Book.of("1234567891", "", "Author", 10.0);
        var violations = validator.validate(book);
        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The book title must be defined.");
    }

    @Test
    void nullPriceFailsValidation() {
        var book = Book.of("1234567891", "Title", "Author", null);
        var violations = validator.validate(book);
        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The book price must be defined.");
    }

    @Test
    void negativePriceFailsValidation() {
        var book = Book.of("1234567891", "Title", "Author", -10.0);
        var violations = validator.validate(book);
        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The book price must be greater than zero.");
    }
}