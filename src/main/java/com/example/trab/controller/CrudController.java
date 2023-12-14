package com.example.trab.controller;

import com.example.trab.entity.Author;
import com.example.trab.entity.Book;
import com.example.trab.service.CrudService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Log4j2
@Controller
@AllArgsConstructor
public class CrudController {

    private final CrudService service;
    @GetMapping("/")
    public String getHome() {
        return "home";
    }

    @GetMapping("/authors")
    public String author(Model model){
        List<Author> authors = service.getAllAuthors();
        model.addAttribute("author", authors);
        return "author";
    }

    @GetMapping("/books")
    public String book(Model model){
        List<Book> books = service.findAll();
        model.addAttribute("book", books);
        return "book";
    }

    @GetMapping("/book")
    public String book(Model model, Integer id){
        Book book = service.findById(id);
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping("/booksearch")
    public String bookSearch(Model model, @RequestParam("title") String title){
        List<Book> books = service.findByPartialTitle(title);
        if (books != null) {
            model.addAttribute("book", books);
        } else {
            model.addAttribute("book", Collections.emptyList());  // Adiciona uma lista vazia em caso de nulo
        }
        return "book";
    }

    @GetMapping("/authorsearch")
    public String authorSearch(Model model, @RequestParam("name") String partialName){
        List<Author> authors = service.findByPartialName(partialName);

        if (authors != null) {
            model.addAttribute("authors", authors);
        } else {
            model.addAttribute("authors", Collections.emptyList());  // Adiciona uma lista vazia em caso de nulo
        }

        return "authorsearch";
    }

    @GetMapping("/newauthor")
    public String newAuthorForm(Author author) {
        return "newauthor";
    }
    @PostMapping("/newauthor")
    public String newAuthor(@ModelAttribute("author") Author author, RedirectAttributes redirectAttributes) {
        log.info("Entrou no cadastro de autor");
        Author addAuthor = service.addAuthor(author);
        redirectAttributes.addFlashAttribute("successMessage", "Author added successfully!");
        return "redirect:/";
    }

    @GetMapping("/newbook")
    public String newBookForm(Model model) {
        List<Author> authors = service.getAllAuthors();
        model.addAttribute("authors", authors);
        model.addAttribute("book", new Book());
        return "newbook";
    }


    @PostMapping("/newbook")
    public String newBook(@ModelAttribute("book") Book book, @RequestParam("author_id") Integer author_id,
                          RedirectAttributes redirectAttributes) {
        Author author = service.findAuthorById(author_id);
        book.setAuthor(author);
        Book addBook = service.addBook(book);
        redirectAttributes.addFlashAttribute("successMessage", "Book added successfully!");

        return "redirect:/";
    }


}
