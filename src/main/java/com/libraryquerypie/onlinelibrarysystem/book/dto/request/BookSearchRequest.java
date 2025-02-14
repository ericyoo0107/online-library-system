package com.libraryquerypie.onlinelibrarysystem.book.dto.request;

import com.libraryquerypie.onlinelibrarysystem.book.dto.BookSortBy;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookSearchRequest {

    @Schema(description = "제목", example = "자바의 정석")
    private String title;

    @Schema(description = "작가", example = "남궁성")
    private String author;

    @Schema(description = "정렬 기준", example = "TITLE")
    private BookSortBy sortby;

    @Schema(description = "태그", example = "programming")
    private String tag;

    @Builder
    public BookSearchRequest(String title, String author, BookSortBy sortby, String tag) {
        this.title = title;
        this.author = author;
        this.sortby = sortby;
        this.tag = tag;
    }
}
