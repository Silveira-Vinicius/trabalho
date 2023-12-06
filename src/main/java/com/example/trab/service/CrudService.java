package com.example.trab.service;

import com.example.trab.entity.Author;
import com.example.trab.entity.Book;
import com.example.trab.exception.BookNotFoundException;
import com.example.trab.repository.AuthorRepository;
import com.example.trab.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CrudService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List <Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    public Book findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id null when fetching for a book.");
        }
        return bookRepository.findById(id).orElseThrow(
                () -> new BookNotFoundException("No book found for id " + id)
        );
    }
}
