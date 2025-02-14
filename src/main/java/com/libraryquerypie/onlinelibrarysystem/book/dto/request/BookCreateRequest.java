package com.libraryquerypie.onlinelibrarysystem.book.dto.request;

import com.libraryquerypie.onlinelibrarysystem.entity.Book;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class BookCreateRequest {

    @Length(min = 13, max = 13, message = "ISBN은 13자리로 입력 해주세요.")
    @Schema(description = "isbn", example = "9311113104314")
    private String isbn;

    @NotBlank(message = "제목 입력은 필수입니다.")
    @Schema(description = "제목", example = "자바의 정석")
    private String title;

    @NotBlank(message = "작가 입력은 필수입니다.")
    @Schema(description = "작가", example = "남궁성")
    private String author;

    @NotNull(message = "출판일 입력은 필수입니다.")
    @Schema(description = "출판일", example = "2021-10-10")
    private LocalDate publishDate;

    @Schema(description = "태그", example = "programming")
    private String tag;

    @Builder
    public BookCreateRequest(String isbn, String title, String author, LocalDate publishDate, String tag) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
        this.tag = tag;
    }

    public static Book toEntity(BookCreateRequest bookCreateRequest) {
        return Book.builder()
                .ISBN(bookCreateRequest.getIsbn())
                .title(bookCreateRequest.getTitle())
                .author(bookCreateRequest.getAuthor())
                .publishDate(bookCreateRequest.getPublishDate())
                .tag(bookCreateRequest.getTag())
                .build();
    }
}
