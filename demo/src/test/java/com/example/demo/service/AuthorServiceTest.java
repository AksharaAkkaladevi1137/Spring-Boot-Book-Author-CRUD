package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthorService Unit Tests")
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author author1;
    private Author author2;

    @BeforeEach
    void setUp() {
        author1 = new Author(1L, "George Orwell", "British", 1903);
        author2 = new Author(2L, "J.K. Rowling", "British", 1965);
    }

    @Test
    @DisplayName("getAllAuthors() should return all authors from repository")
    void testGetAllAuthors() {
        // Given
        List<Author> expectedAuthors = Arrays.asList(author1, author2);
        when(authorRepository.findAll()).thenReturn(expectedAuthors);

        // When
        List<Author> actualAuthors = authorService.getAllAuthors();

        // Then
        assertNotNull(actualAuthors);
        assertEquals(2, actualAuthors.size());
        assertEquals("George Orwell", actualAuthors.get(0).getName());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getAuthorById() should return author when found")
    void testGetAuthorById_found() {
        // Given
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));

        // When
        Optional<Author> result = authorService.getAuthorById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals("George Orwell", result.get().getName());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getAuthorById() should return empty when author not found")
    void testGetAuthorById_notFound() {
        // Given
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        Optional<Author> result = authorService.getAuthorById(99L);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("saveAuthor() should persist and return the saved author")
    void testSaveAuthor() {
        // Given
        Author newAuthor = new Author(null, "Ernest Hemingway", "American", 1899);
        Author savedAuthor = new Author(3L, "Ernest Hemingway", "American", 1899);
        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);

        // When
        Author result = authorService.saveAuthor(newAuthor);

        // Then
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("Ernest Hemingway", result.getName());
        verify(authorRepository, times(1)).save(newAuthor);
    }

    @Test
    @DisplayName("updateAuthor() should update existing author successfully")
    void testUpdateAuthor_success() {
        // Given
        Author updatedAuthor = new Author(1L, "George Orwell (Updated)", "British", 1903);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));
        when(authorRepository.save(any(Author.class))).thenReturn(updatedAuthor);

        // When
        Author result = authorService.updateAuthor(updatedAuthor);

        // Then
        assertNotNull(result);
        assertEquals("George Orwell (Updated)", result.getName());
        verify(authorRepository, times(1)).findById(1L);
        verify(authorRepository, times(1)).save(updatedAuthor);
    }

    @Test
    @DisplayName("updateAuthor() should throw RuntimeException when author not found")
    void testUpdateAuthor_notFound() {
        // Given
        Author nonExistentAuthor = new Author(99L, "Unknown", "Unknown", 2000);
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authorService.updateAuthor(nonExistentAuthor));
        assertTrue(exception.getMessage().contains("Author not found with id: 99"));
        verify(authorRepository, never()).save(any());
    }

    @Test
    @DisplayName("deleteAuthor() should call repository deleteById")
    void testDeleteAuthor() {
        // Given
        doNothing().when(authorRepository).deleteById(1L);

        // When
        authorService.deleteAuthor(1L);

        // Then
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("findByNationality() should return authors of given nationality")
    void testFindByNationality() {
        // Given
        List<Author> britishAuthors = Arrays.asList(author1, author2);
        when(authorRepository.findByNationality("British")).thenReturn(britishAuthors);

        // When
        List<Author> result = authorService.findByNationality("British");

        // Then
        assertEquals(2, result.size());
        result.forEach(a -> assertEquals("British", a.getNationality()));
    }
}
