package com.example.trab.controller;

import com.example.trab.entity.Author;
import com.example.trab.entity.Book;
import com.example.trab.service.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
}
