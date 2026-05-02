package com.example.demo.repository;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("BookRepository Integration Tests")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private Author author1;
    private Author author2;

    @BeforeEach
    void setUp() {
        // Clear and set up fresh data for each test
        bookRepository.deleteAll();
        authorRepository.deleteAll();

        author1 = new Author();
        author1.setName("George Orwell");
        author1.setNationality("British");
        author1.setBirthYear(1903);
        author1 = authorRepository.save(author1);

        author2 = new Author();
        author2.setName("J.K. Rowling");
        author2.setNationality("British");
        author2.setBirthYear(1965);
        author2 = authorRepository.save(author2);

        Book book1 = new Book();
        book1.setTitle("1984");
        book1.setGenre("Dystopian");
        book1.setPublishedYear(1949);
        book1.setIsbn("978-0451524935");
        book1.setAuthor(author1);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("Animal Farm");
        book2.setGenre("Political Satire");
        book2.setPublishedYear(1945);
        book2.setIsbn("978-0451526342");
        book2.setAuthor(author1);
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setTitle("Harry Potter");
        book3.setGenre("Fantasy");
        book3.setPublishedYear(1997);
        book3.setIsbn("978-0439708180");
        book3.setAuthor(author2);
        bookRepository.save(book3);
    }

    @Test
    @DisplayName("findAllBooksWithAuthors() - INNER JOIN should return all books with their authors")
    void testFindAllBooksWithAuthors() {
        // When
        List<Book> books = bookRepository.findAllBooksWithAuthors();

        // Then
        assertNotNull(books);
        assertEquals(3, books.size());
        // All books must have authors loaded (join ensured)
        books.forEach(book -> assertNotNull(book.getAuthor(),
                "Each book must have an associated author from JOIN"));
    }

    @Test
    @DisplayName("findBooksByAuthorName() should return books matching author name exactly")
    void testFindBooksByAuthorName() {
        // When
        List<Book> orwellBooks = bookRepository.findBooksByAuthorName("George Orwell");

        // Then
        assertEquals(2, orwellBooks.size());
        orwellBooks.forEach(b ->
                assertEquals("George Orwell", b.getAuthor().getName()));
    }

    @Test
    @DisplayName("findBooksByAuthorName() should return empty list for unknown author")
    void testFindBooksByAuthorName_noMatch() {
        // When
        List<Book> result = bookRepository.findBooksByAuthorName("Unknown Author");

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("existsByIsbn() should return true for existing ISBN")
    void testExistsByIsbn_exists() {
        assertTrue(bookRepository.existsByIsbn("978-0451524935"));
    }

    @Test
    @DisplayName("existsByIsbn() should return false for non-existing ISBN")
    void testExistsByIsbn_notExists() {
        assertFalse(bookRepository.existsByIsbn("000-0000000000"));
    }

    @Test
    @DisplayName("existsByIsbnAndIdNot() should detect ISBN conflict on different books")
    void testExistsByIsbnAndIdNot() {
        // Get book2's id
        List<Book> books = bookRepository.findAll();
        Book book2 = books.stream()
                .filter(b -> b.getTitle().equals("Animal Farm"))
                .findFirst().orElseThrow();

        // book2 trying to use book1's ISBN should conflict
        assertTrue(bookRepository.existsByIsbnAndIdNot("978-0451524935", book2.getId()));

        // book2 using its own ISBN should NOT conflict
        assertFalse(bookRepository.existsByIsbnAndIdNot("978-0451526342", book2.getId()));
    }

    @Test
    @DisplayName("save() should persist a new book correctly")
    void testSaveBook() {
        // Given
        Book newBook = new Book();
        newBook.setTitle("Brave New World");
        newBook.setGenre("Dystopian");
        newBook.setPublishedYear(1932);
        newBook.setIsbn("978-0060850524");
        newBook.setAuthor(author1);

        // When
        Book saved = bookRepository.save(newBook);

        // Then
        assertNotNull(saved.getId());
        assertEquals("Brave New World", saved.getTitle());
        assertEquals(4, bookRepository.count());
    }
}
