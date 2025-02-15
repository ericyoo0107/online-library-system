package com.libraryquerypie.onlinelibrarysystem.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BorrowStatus {
    BORROW("대출 중"), RETURN("대출 가능");

    private final String status;
}
