package com.example.trab.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "db1", name = "book")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    private String title;

    private Integer publication_year;

    private Integer pages;

    private Integer author_id;

    @ManyToOne
    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    private Author author;
}
