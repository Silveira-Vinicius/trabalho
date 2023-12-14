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
import java.util.Optional;

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
    public String newBook(@ModelAttribute("book") Book book, @RequestParam("author_id") Integer author_id,               //Vinicius Barbosa da Silveira
                          RedirectAttributes redirectAttributes) {
        Author author = service.findAuthorById(author_id);
        book.setAuthor(author);
        Book addBook = service.addBook(book);
        redirectAttributes.addFlashAttribute("successMessage", "Book added successfully!");

        return "redirect:/";
    }


    @GetMapping("/deleteauthor")
    public String deleteAuthorForm(Model model) {
        List<Author> authors = service.getAllAuthors();
        model.addAttribute("authors", authors);
        return "deleteauthor";
    }

    @PostMapping("/deleteauthor")
    public String deleteAuthor(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        service.deleteAuthorById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Author deleted successfully!");
        return "redirect:/";
    }

    @GetMapping("/editbook/{id}")
    public String editBookForm(Model model, @PathVariable("id") Integer id) {
        Book book = service.findById(id);
        List<Author> authors = service.getAllAuthors();
        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        return "editbook";
    }

    @PostMapping("/editbook")
    public String editBook(@ModelAttribute("book") Book updatedBook, RedirectAttributes redirectAttributes) {
        Book existingBook = service.findById(updatedBook.getId());

        if (existingBook != null) {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setPublicationYear(updatedBook.getPublicationYear());
            existingBook.setPages(updatedBook.getPages());

            Author author = service.findAuthorById(updatedBook.getAuthor().getId());
            existingBook.setAuthor(author);

            service.editBook(existingBook);

            redirectAttributes.addFlashAttribute("successMessage", "Book edited successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Book not found for editing.");
        }

        return "redirect:/";
    }
    @GetMapping("/booksearch")
    public String bookSearch(Model model, @RequestParam("title") String title){
        List<Book> books = service.findByPartialTitle(title);
        //log.info("Books found: {}", books);  // Adicione esta linha para verificar se os livros são encontrados

        if (books != null) {
            model.addAttribute("books", books);
        } else {
            model.addAttribute("books", Collections.emptyList());
        }

        return "booksearch";
    }



    @PostMapping("/editauthor")
    public String editAuthor(@ModelAttribute("author") Author updatedAuthor, RedirectAttributes redirectAttributes) {
        Author existingAuthor = service.findAuthorById(updatedAuthor.getId());

        if (existingAuthor != null) {
            existingAuthor.setFirst_name(updatedAuthor.getFirst_name());
            existingAuthor.setLast_name(updatedAuthor.getLast_name());
            existingAuthor.setNationality(updatedAuthor.getNationality());

            service.editAuthor(existingAuthor);

            redirectAttributes.addFlashAttribute("successMessage", "Author edited successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Author not found for editing.");
        }

        return "redirect:/";
    }

    @GetMapping("/editauthor")
    public String editAuthorForm(Model model, @RequestParam("id") Integer id) {
        Author author = service.findAuthorById(id);
        model.addAttribute("author", author);
        return "editauthor";
    }

    @GetMapping("/deletebook")
    public String deleteBookForm(Model model) {
        List<Book> books = service.findAll();
        model.addAttribute("books", books);
        return "deletebook";
    }

    @PostMapping("/deletebook")
    public String deleteBook(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        service.deleteBookById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Book deleted successfully!");
        return "redirect:/";
    }


}
