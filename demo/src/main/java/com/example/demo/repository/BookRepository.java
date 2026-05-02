package com.example.demo.repository;

import com.example.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Custom JPQL - INNER JOIN: fetch all books along with their author details
     * Fulfills the "inner join between both entities" requirement
     */
    @Query("SELECT b FROM Book b JOIN b.author a ORDER BY a.name, b.title")
    List<Book> findAllBooksWithAuthors();

    /**
     * Custom JPQL - find books by author name (inner join)
     */
    @Query("SELECT b FROM Book b JOIN b.author a WHERE a.name = :authorName")
    List<Book> findBooksByAuthorName(@Param("authorName") String authorName);

    /**
     * Custom JPQL - find books by genre
     */
    List<Book> findByGenreIgnoreCase(String genre);

    /**
     * Check if ISBN already exists (for integrity checking)
     */
    boolean existsByIsbn(String isbn);

    /**
     * Check if ISBN exists for a different book (used during update)
     */
    @Query("SELECT COUNT(b) > 0 FROM Book b WHERE b.isbn = :isbn AND b.id <> :id")
    boolean existsByIsbnAndIdNot(@Param("isbn") String isbn, @Param("id") Long id);
}
