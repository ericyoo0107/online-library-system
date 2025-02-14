package com.libraryquerypie.onlinelibrarysystem.book.dto.response;

import com.libraryquerypie.onlinelibrarysystem.entity.Book;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class BookSearchResponse {

    @Schema(description = "isbn", example = "9311113104314")
    private String ISBN;

    @Schema(description = "제목", example = "자바의 정석")
    private String title;

    @Schema(description = "작가", example = "남궁성")
    private String author;

    @Schema(description = "출판일", example = "2021-10-10")
    private LocalDate publishDate;

    @Schema(description = "태그", example = "programming")
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
