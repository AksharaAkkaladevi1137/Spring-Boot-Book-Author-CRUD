package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
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
@DisplayName("BookService Unit Tests")
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Author author;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        author = new Author(1L, "George Orwell", "British", 1903);
        book1 = new Book(1L, "1984", "Dystopian", 1949, "978-0451524935", author);
        book2 = new Book(2L, "Animal Farm", "Political Satire", 1945, "978-0451526342", author);
    }

    @Test
    @DisplayName("getAllBooks() should return all books from repository")
    void testGetAllBooks() {
        // Given
        List<Book> expected = Arrays.asList(book1, book2);
        when(bookRepository.findAll()).thenReturn(expected);

        // When
        List<Book> result = bookService.getAllBooks();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getAllBooksWithAuthors() should use custom join query")
    void testGetAllBooksWithAuthors() {
        // Given
        List<Book> expected = Arrays.asList(book1, book2);
        when(bookRepository.findAllBooksWithAuthors()).thenReturn(expected);

        // When
        List<Book> result = bookService.getAllBooksWithAuthors();

        // Then
        assertEquals(2, result.size());
        assertEquals("George Orwell", result.get(0).getAuthor().getName());
        verify(bookRepository, times(1)).findAllBooksWithAuthors();
    }

    @Test
    @DisplayName("getBookById() should return book when found")
    void testGetBookById_found() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        // When
        Optional<Book> result = bookService.getBookById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals("1984", result.get().getTitle());
    }

    @Test
    @DisplayName("saveBook() should save book with unique ISBN")
    void testSaveBook_success() throws Exception {
        // Given
        Book newBook = new Book(null, "Brave New World", "Dystopian", 1932, "978-0060850524", author);
        when(bookRepository.existsByIsbn("978-0060850524")).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(new Book(3L, "Brave New World", "Dystopian", 1932, "978-0060850524", author));

        // When
        Book result = bookService.saveBook(newBook);

        // Then
        assertNotNull(result);
        assertEquals(3L, result.getId());
        verify(bookRepository, times(1)).save(newBook);
    }

    @Test
    @DisplayName("saveBook() should throw exception when ISBN already exists")
    void testSaveBook_duplicateIsbn() {
        // Given
        Book duplicate = new Book(null, "Another Book", "Fiction", 2020, "978-0451524935", author);
        when(bookRepository.existsByIsbn("978-0451524935")).thenReturn(true);

        // When / Then
        Exception exception = assertThrows(Exception.class,
                () -> bookService.saveBook(duplicate));
        assertTrue(exception.getMessage().contains("ISBN"));
        assertTrue(exception.getMessage().contains("already exists"));
        verify(bookRepository, never()).save(any());
    }

    @Test
    @DisplayName("updateBook() should update when book exists and ISBN is unique")
    void testUpdateBook_success() throws Exception {
        // Given
        Book updatedBook = new Book(1L, "1984 (Revised)", "Dystopian", 1949, "978-0451524935", author);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        when(bookRepository.existsByIsbnAndIdNot("978-0451524935", 1L)).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        // When
        Book result = bookService.updateBook(updatedBook);

        // Then
        assertNotNull(result);
        assertEquals("1984 (Revised)", result.getTitle());
    }

    @Test
    @DisplayName("updateBook() should throw exception when book not found")
    void testUpdateBook_notFound() {
        // Given
        Book nonExistent = new Book(99L, "Unknown", "Fiction", 2020, "978-9999999999", author);
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        Exception exception = assertThrows(Exception.class,
                () -> bookService.updateBook(nonExistent));
        assertTrue(exception.getMessage().contains("Book not found"));
        verify(bookRepository, never()).save(any());
    }

    @Test
    @DisplayName("updateBook() should throw exception when duplicate ISBN on different book")
    void testUpdateBook_duplicateIsbnOnDifferentBook() {
        // Given — book2 tries to use book1's ISBN
        Book conflicting = new Book(2L, "Animal Farm", "Political Satire", 1945, "978-0451524935", author);
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book2));
        when(bookRepository.existsByIsbnAndIdNot("978-0451524935", 2L)).thenReturn(true);

        // When / Then
        Exception exception = assertThrows(Exception.class,
                () -> bookService.updateBook(conflicting));
        assertTrue(exception.getMessage().contains("ISBN"));
    }

    @Test
    @DisplayName("findBooksByAuthorName() should return matching books")
    void testFindBooksByAuthorName() {
        // Given
        when(bookRepository.findBooksByAuthorName("George Orwell")).thenReturn(Arrays.asList(book1, book2));

        // When
        List<Book> result = bookService.findBooksByAuthorName("George Orwell");

        // Then
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findBooksByAuthorName("George Orwell");
    }
}
