package com.example.trab.repository;

import com.example.trab.entity.Author;
import com.example.trab.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByTitle(String title);

    @Query("SELECT b FROM Book b WHERE b.title LIKE %:title%")
    List<Book> findByPartialTitle(@Param("title") String title);


    @Query("SELECT b FROM Book b WHERE b.author = :author")
    List<Book> findByAuthor(@Param("author") Author author);
}
