package com.bladespring.bookshop.catalogservice.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bladespring.bookshop.catalogservice.exception.BookAlreadyExistsException;
import com.bladespring.bookshop.catalogservice.exception.BookNotFoundException;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> viewBookList() {
        return bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn) {
        var b = bookRepository.findByIsbn(isbn);
        if (b.isPresent())
            return b.get();
        else
            throw new BookNotFoundException(isbn);
    }

    public void removeBookFromCatalog(String isbn) {
        var b = bookRepository.findByIsbn(isbn);
        if (b.isPresent()) {
            bookRepository.delete(b.get());
        }
    }

    public Book addBookToCatalog(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new BookAlreadyExistsException(book.getIsbn());
        }
        return bookRepository.save(book);
    }

    public Book editBookDetails(String isbn, Book book) {
        return bookRepository.findByIsbn(isbn)
                .map(existingBook -> {
                    var bookToUpdate = new Book(
                            existingBook.getId(),
                            existingBook.getIsbn(),
                            book.getTitle(),
                            book.getAuthor(),
                            book.getPrice(),
                            existingBook.getCreatedDate(),
                            existingBook.getLastModifiedDate(),
                            existingBook.getVersion());
                    return bookRepository.save(bookToUpdate);
                }).orElseGet(() -> addBookToCatalog(book));
    }
}
