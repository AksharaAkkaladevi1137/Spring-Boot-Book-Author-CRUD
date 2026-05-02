package com.example.demo.service;

import com.example.demo.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
    List<Book> getAllBooksWithAuthors();
    Optional<Book> getBookById(Long id);
    Book saveBook(Book book) throws Exception;
    Book updateBook(Book book) throws Exception;
    void deleteBook(Long id);
    List<Book> findBooksByAuthorName(String authorName);
}
