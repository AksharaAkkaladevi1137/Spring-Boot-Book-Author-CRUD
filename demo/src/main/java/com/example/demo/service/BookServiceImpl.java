package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooksWithAuthors() {
        // Uses the custom JPQL inner join query
        return bookRepository.findAllBooksWithAuthors();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book saveBook(Book book) throws Exception {
        // Check for duplicate ISBN before saving
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new Exception("A book with ISBN '" + book.getIsbn() + "' already exists. ISBN must be unique.");
        }
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Data integrity violation: " + e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public Book updateBook(Book book) throws Exception {
        // Verify the book exists
        bookRepository.findById(book.getId())
                .orElseThrow(() -> new Exception("Book not found with id: " + book.getId()));

        // Check ISBN uniqueness excluding current book
        if (bookRepository.existsByIsbnAndIdNot(book.getIsbn(), book.getId())) {
            throw new Exception("A book with ISBN '" + book.getIsbn() + "' already exists. ISBN must be unique.");
        }
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Data integrity violation: " + e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findBooksByAuthorName(String authorName) {
        return bookRepository.findBooksByAuthorName(authorName);
    }
}
