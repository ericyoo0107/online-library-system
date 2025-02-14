package com.libraryquerypie.onlinelibrarysystem.book.dto.response;

import com.libraryquerypie.onlinelibrarysystem.entity.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class BookSearchResponse {
    private String ISBN;
    private String title;
    private String author;
    private LocalDate publishDate;
    private String tag;

    @Builder
    public BookSearchResponse(String ISBN, String title, String author, LocalDate publishDate, String tag) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
        this.tag = tag;
    }

    public static BookSearchResponse fromEntity(Book book) {
        return BookSearchResponse.builder()
                .ISBN(book.getISBN())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publishDate(book.getPublishDate())
                .tag(book.getTag())
                .build();
    }
}
