package com.libraryquerypie.onlinelibrarysystem.book.dto.request;

import com.libraryquerypie.onlinelibrarysystem.book.dto.BookSortBy;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookSearchRequest {
    private String title;
    private String author;
    private BookSortBy sortby;
    private String tag;

    @Builder
    public BookSearchRequest(String title, String author, BookSortBy sortby, String tag) {
        this.title = title;
        this.author = author;
        this.sortby = sortby;
        this.tag = tag;
    }
}
