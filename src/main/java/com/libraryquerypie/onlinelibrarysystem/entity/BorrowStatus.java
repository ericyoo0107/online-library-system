package com.libraryquerypie.onlinelibrarysystem.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BorrowStatus {
    BORROW("대출 중"), RETURN("반납 완료");

    private final String status;
}
