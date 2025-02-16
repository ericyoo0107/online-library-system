package com.libraryquerypie.onlinelibrarysystem.borrow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class BookBorrowRequest {
    @NotNull(message = "빌릴 책 ID를 입력하세요.")
    @Schema(description = "빌릴 책 ID", example = "1")
    private Long bookId;
    @NotNull(message = "반납 날짜를 입력하세요.")
    @Schema(description = "반납 날짜", example = "2025-03-10")
    private LocalDate returnDate;

    @Builder
    public BookBorrowRequest(Long bookId, LocalDate returnDate) {
        this.bookId = bookId;
        this.returnDate = returnDate;
    }
}
