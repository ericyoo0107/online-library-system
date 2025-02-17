package com.libraryquerypie.onlinelibrarysystem.exception.custom;

import com.libraryquerypie.onlinelibrarysystem.exception.ErrorCode;
import lombok.Getter;

@Getter
public class AlreadyBorrowException extends BaseException {

    private String bookId;

    public AlreadyBorrowException(String bookId) {
        super(ErrorCode.BOOK_ALREADY_BORROWED);
        this.bookId = bookId;
    }
}
