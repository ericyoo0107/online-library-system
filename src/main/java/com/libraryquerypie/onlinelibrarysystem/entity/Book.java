package com.libraryquerypie.onlinelibrarysystem.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "books")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "ISBN", nullable = false, unique = true, length = 13)
    private String ISBN;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    @Column(name ="tag", nullable = true)
    private String tag;

    @Builder
    public Book(String ISBN, String title, String author, LocalDate publishDate, String tag) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
        this.tag = tag;
    }
}
