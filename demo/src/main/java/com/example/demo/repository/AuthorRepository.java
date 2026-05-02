package com.example.demo.repository;

import com.example.demo.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Custom query: find authors by nationality
     */
    List<Author> findByNationality(String nationality);

    /**
     * Custom query: find authors by name containing (case-insensitive)
     */
    List<Author> findByNameContainingIgnoreCase(String name);
}
