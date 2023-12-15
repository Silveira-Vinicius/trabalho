package com.example.trab;

import com.example.trab.controller.request.AuthorRequest;
import com.example.trab.entity.Author;
import com.example.trab.entity.Book;
import com.example.trab.exception.AuthorNotFoundException;
import com.example.trab.exception.BookNotFoundException;
import com.example.trab.exception.InvalidAuthorException;
import com.example.trab.repository.AuthorRepository;
import com.example.trab.repository.BookRepository;
import com.example.trab.service.CrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CrudServiceTest {
    @InjectMocks
    private CrudService service;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("#findById > When the id is null > Throw an exception")
    void findByIdWhenTheIdIsNullThrowAnException() {
        assertThrows(IllegalArgumentException.class, () ->
                service.findById(null));
    }

    @Test
    @DisplayName("#findById > When the id is not null > When a book is found > Return the book")
    void findByIdWhenTheIdIsNotNullWhenABookIFoundReturnTheBook (){
        CrudService service = new CrudService(bookRepository, authorRepository);
        when(bookRepository.findById(1)).thenReturn(Optional.of(Book.builder()
                .id(1)
                .title("Livro")
                .build()));
        Book response = service.findById(1);
        assertAll(
                () -> assertEquals(1, response.getId()),
                () -> assertEquals("Livro", response.getTitle())
        );
    }

    @Test
    @DisplayName("#findById > When the id is not null > When no book is found > Throw an exception")
    void findByIdWhenTheIdIsNotNullWhenNoBookIsFoundThrowAnException() {
        when(bookRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () ->
                service.findById(2));
    }

    @Test
    @DisplayName("#findAuthorById > When the id is null > Throw an exception")
    void findAuthorByIdWhenTheIdIsNullThrowAnException() {
        assertThrows(IllegalArgumentException.class, () ->
                service.findAuthorById(null));
    }

    @Test
    @DisplayName("#findAuthorById > When the id is not null > When a author is found > Return the author")
    void findAuthorByIdWhenTheIdIsNotNullWhenAAuthorIsFoundReturnTheAuthor (){
        CrudService service = new CrudService(bookRepository, authorRepository);
        when(authorRepository.findById(1)).thenReturn(Optional.of(Author.builder()
                        .first_name("Vinicius")
                        .last_name("Silveira")
                        .nationality("Brasileiro")
                .build()));
        Author response = service.findAuthorById(1);
        assertAll(
                () -> assertEquals("Vinicius", response.getFirst_name()),
                () -> assertEquals("Silveira", response.getLast_name()),
                () -> assertEquals("Brasileiro", response.getNationality())
        );
    }

    @Test
    @DisplayName("#findAuthorById > When the id is not null > When no author is found > Throw an exception")
    void findAuthorByIdWhenTheIdIsNotNullWhenNoAuthorIsFoundThrowAnException() {
        when(authorRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(AuthorNotFoundException.class, () ->
                service.findAuthorById(2));
    }

    @Test
    @DisplayName("#findByTitle > When the title is null > Throw an exception")
    void findByTitleWhenTheTitleIsNullThrowAnException() {
        assertThrows(IllegalArgumentException.class, () ->
                service.findByTitle(null));
    }

    @Test
    @DisplayName("#findByTitle > When the title is not null > When a book is found > Return the book")
    void findByTitleWhenTheTitleIsNotNullWhenABookIFoundReturnTheBook (){
        CrudService service = new CrudService(bookRepository, authorRepository);
        when(bookRepository.findByTitle("Livro")).thenReturn(Optional.of(Book.builder()
                .id(1)
                .title("Livro")
                .build()));
        Book response = service.findByTitle("Livro");
        assertAll(
                () -> assertEquals(1, response.getId()),
                () -> assertEquals("Livro", response.getTitle())
        );
    }

    @Test
    @DisplayName("#findByTitle > When the title is not null > When no book is found > Throw an exception")
    void findByTitleWhenTheTitleIsNotNullWhenNoBookIsFoundThrowAnException() {
        when(bookRepository.findByTitle("Livro")).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () ->
                service.findByTitle("Livro"));
    }

    @Test
    @DisplayName("#deleteAuthorById > When the id is null > Throw an exception")
    void deleteAuthorByIdWhenTheIdIsNullThrowAnException() {
        assertThrows(IllegalArgumentException.class, () ->
                service.deleteAuthorById(null));
    }

    @Test
    @DisplayName("#deleteAuthorById > When the id is not null > When a author is found > Return the author")
    void deleteAuthorByIdWhenTheIdIsNotNullWhenAAuthorIsFoundReturnTheAuthor () {
        Author author = mock(Author.class);
        CrudService service = new CrudService(null, authorRepository);

        Integer authorIdToDelete = 1;

        service.deleteAuthorById(authorIdToDelete);

        verify(authorRepository).deleteById(authorIdToDelete);

    }

    @Test
    @DisplayName("#deleteBookById > When the id is null > Throw an exception")
    void deleteBookByIdWhenTheIdIsNullThrowAnException() {
        assertThrows(IllegalArgumentException.class, () ->
                service.deleteBookById(null));
    }

    @Test
    @DisplayName("#deleteBookById > When the id is not null > When a book is found > Return the book")
    void deleteBookByIdWhenTheIdIsNotNullWhenABookIsFoundReturnTheBook () {
        Book book = mock(Book.class);
        CrudService service = new CrudService(bookRepository, null);

        Integer bookIdToDelete = 1;

        service.deleteBookById(bookIdToDelete);

        verify(bookRepository).deleteById(bookIdToDelete);

    }

    @Test
    @DisplayName("#addAuthor > When the author is null > Throw an exception")
    void addAuthorWhenTheAuthorIsNullThrowAnException() {
        assertThrows(InvalidAuthorException.class, () ->
                service.addAuthor((Author) null));
    }

    @Test
    @DisplayName("#addAuthor > When the author is not null > When a author is found > Return the author")
    void addAuthorWhenTheAuthorIsNotNullWhenAAuthorIsFoundReturnTheAuthor () {
        Author author = mock(Author.class);
        CrudService service = new CrudService(null, authorRepository);

        service.addAuthor(author);

        verify(authorRepository).save(author);

    }

    @Test
    @DisplayName("#addBook > When the book is null > Throw an exception")
    void addBookWhenTheBookIsNullThrowAnException() {
        assertThrows(InvalidAuthorException.class, () ->
                service.addBook((Book) null));
    }

    @Test
    @DisplayName("#addBook > When the book is not null > When a book is found > Return the book")
    void addBookWhenTheBookIsNotNullWhenABookIsFoundReturnTheBook () {
        Book book = mock(Book.class);
        CrudService service = new CrudService(bookRepository, null);

        service.addBook(book);

        verify(bookRepository).save(book);

    }


}
