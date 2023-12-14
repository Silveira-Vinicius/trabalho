package com.example.trab.repository;

import com.example.trab.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query("SELECT a FROM Author a WHERE LOWER(a.first_name) LIKE LOWER(concat('%', :name, '%')) " +
            "OR LOWER(a.last_name) LIKE LOWER(concat('%', :name, '%'))")
    List<Author> findByPartialName(@Param("name") String name);

}
