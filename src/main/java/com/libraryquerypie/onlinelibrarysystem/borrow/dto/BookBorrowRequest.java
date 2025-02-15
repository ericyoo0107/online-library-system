package com.libraryquerypie.onlinelibrarysystem.borrow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class BookBorrowRequest {
    @NotNull(message = "빌릴 책 ID를 입력하세요.")
    private Long bookId;
    @NotNull(message = "반납 날짜를 입력하세요.")
    private LocalDate returnDate;
}
