package com.example.demo.service;

import com.example.demo.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> getAllAuthors();
    Optional<Author> getAuthorById(Long id);
    Author saveAuthor(Author author);
    Author updateAuthor(Author author);
    void deleteAuthor(Long id);
    List<Author> findByNationality(String nationality);
}
