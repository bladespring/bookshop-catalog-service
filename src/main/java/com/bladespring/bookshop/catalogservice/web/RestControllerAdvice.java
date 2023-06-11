package com.bladespring.bookshop.catalogservice.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bladespring.bookshop.catalogservice.exception.BookAlreadyExistsException;
import com.bladespring.bookshop.catalogservice.exception.BookNotFoundException;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class ValidationError {
    private String message;
    private Map<String, String> errors;

    public ValidationError(Map<String, String> errors) {
        this.errors = errors;
        this.message = "Validation Error";
    }
}

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationError methodArgumentNotValidHandler(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<String, String>();
        ex.getAllErrors().forEach(error -> {
            var errorField = (FieldError) error;
            errors.put(errorField.getField(), errorField.getDefaultMessage());
        });
        return new ValidationError(errors);
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationError bookAlreadyExistsHandler(BookAlreadyExistsException ex) {
        Map<String, String> errors = new HashMap<String, String>();
        errors.put("isbn", ex.getMessage());
        return new ValidationError(errors);
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> bookNotFoundHandler(BookNotFoundException ex) {
        Map<String, String> errors = new HashMap<String, String>();
        errors.put("error", ex.getMessage());
        return errors;
    }
}
