package com.example.demo.controller;

import com.example.demo.entity.Author;
import com.example.demo.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    /** READ - List all authors */
    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("pageTitle", "All Authors");
        return "authors/list";
    }

    /** CREATE - Show form */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("author", new Author());
        model.addAttribute("pageTitle", "Add New Author");
        model.addAttribute("formAction", "/authors/save");
        model.addAttribute("isEdit", false);
        return "authors/form";
    }

    /** CREATE - Handle submission */
    @PostMapping("/save")
    public String saveAuthor(@ModelAttribute Author author,
                             RedirectAttributes redirectAttributes) {
        try {
            authorService.saveAuthor(author);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Author '" + author.getName() + "' added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error saving author: " + e.getMessage());
            return "redirect:/authors/new";
        }
        return "redirect:/authors";
    }

    /** UPDATE - Show edit form */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model,
                               RedirectAttributes redirectAttributes) {
        return authorService.getAuthorById(id)
                .map(author -> {
                    model.addAttribute("author", author);
                    model.addAttribute("pageTitle", "Edit Author");
                    model.addAttribute("formAction", "/authors/update");
                    model.addAttribute("isEdit", true);
                    return "authors/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage",
                            "Author not found with id: " + id);
                    return "redirect:/authors";
                });
    }

    /** UPDATE - Handle update submission */
    @PostMapping("/update")
    public String updateAuthor(@ModelAttribute Author author,
                               RedirectAttributes redirectAttributes) {
        try {
            authorService.updateAuthor(author);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Author '" + author.getName() + "' updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error updating author: " + e.getMessage());
            return "redirect:/authors/edit/" + author.getId();
        }
        return "redirect:/authors";
    }
}
