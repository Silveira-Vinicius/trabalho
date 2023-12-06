package com.example.trab.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "db1", name = "author")
public class Author implements Serializable {
    @Id
    private Integer id;

    private String first_name;

    private String last_name;

    private String nationality;

    @OneToMany(mappedBy = "author")
    private List<Book> books;
}
