package com.example.demo.controller;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    /** READ - List all books with author join */
    @GetMapping
    public String listBooks(Model model) {
        // Uses custom JPQL inner join query
        List<Book> books = bookService.getAllBooksWithAuthors();
        model.addAttribute("books", books);
        model.addAttribute("pageTitle", "All Books");
        return "books/list";
    }

    /** CREATE - Show form */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("pageTitle", "Add New Book");
        model.addAttribute("formAction", "/books/save");
        model.addAttribute("isEdit", false);
        return "books/form";
    }

    /** CREATE - Handle submission with integrity violation handling */
    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book,
                           @RequestParam("authorId") Long authorId,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        try {
            Author author = authorService.getAuthorById(authorId)
                    .orElseThrow(() -> new Exception("Author not found with id: " + authorId));
            book.setAuthor(author);
            bookService.saveBook(book);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Book '" + book.getTitle() + "' added successfully!");
            return "redirect:/books";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("bookData", book);
            return "redirect:/books/new";
        }
    }

    /** UPDATE - Show edit form */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model,
                               RedirectAttributes redirectAttributes) {
        return bookService.getBookById(id)
                .map(book -> {
                    model.addAttribute("book", book);
                    model.addAttribute("authors", authorService.getAllAuthors());
                    model.addAttribute("pageTitle", "Edit Book");
                    model.addAttribute("formAction", "/books/update");
                    model.addAttribute("isEdit", true);
                    return "books/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage",
                            "Book not found with id: " + id);
                    return "redirect:/books";
                });
    }

    /** UPDATE - Handle update with integrity violation handling */
    @PostMapping("/update")
    public String updateBook(@ModelAttribute Book book,
                             @RequestParam("authorId") Long authorId,
                             RedirectAttributes redirectAttributes) {
        try {
            Author author = authorService.getAuthorById(authorId)
                    .orElseThrow(() -> new Exception("Author not found with id: " + authorId));
            book.setAuthor(author);
            bookService.updateBook(book);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Book '" + book.getTitle() + "' updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/books/edit/" + book.getId();
        }
        return "redirect:/books";
    }
}
