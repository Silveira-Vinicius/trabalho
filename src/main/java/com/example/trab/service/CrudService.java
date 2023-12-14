package com.example.trab.service;

import com.example.trab.controller.request.AuthorRequest;
import com.example.trab.controller.request.BookRequest;
import com.example.trab.entity.Author;
import com.example.trab.entity.Book;
import com.example.trab.exception.AuthorNotFoundException;
import com.example.trab.exception.BookNotFoundException;
import com.example.trab.exception.InvalidAuthorException;
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


    public List<Book> findByPartialTitle(String partialTitle) {
        return bookRepository.findByPartialTitle(partialTitle);
    }

    public List<Author> findByPartialName(String partialName) {
        return authorRepository.findByPartialName(partialName);
    }

    public Book findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id null when fetching for a book.");
        }
        return bookRepository.findById(id).orElseThrow(
                () -> new BookNotFoundException("No book found for id " + id)
        );
    }

    public Book findByTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title null when fetching for a book.");
        }
        return bookRepository.findByTitle(title).orElseThrow(
                () -> new BookNotFoundException("No book found for title " + title)
        );
    }

    public Author addAuthor(Author author) {
        if (author == null ) {
            throw new InvalidAuthorException("Author null when adding an author.");
        }
        return authorRepository.save(author);
    }

    public Author addAuthor(AuthorRequest request) {
        Author author = Author.builder()
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .nationality(request.getNationality())
                .build();
        return addAuthor(author);
    }

    public Book addBook(Book book) {
        if (book == null ) {
            throw new InvalidAuthorException("Book null when adding an book.");
        }
        return bookRepository.save(book);
    }

    public Book addBook(BookRequest request) {
        Book book = Book.builder()
                .title(request.getTitle())
                .publicationYear(request.getPublicationYear())
                .pages(request.getPages())
                .build();
        return addBook(book);
    }

    public Author findAuthorById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id null when fetching for an author.");
        }
        return authorRepository.findById(id).orElseThrow(
                () -> new AuthorNotFoundException("No author found for id " + id)
        );
    }


}
