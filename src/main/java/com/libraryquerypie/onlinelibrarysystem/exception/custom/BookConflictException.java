package com.libraryquerypie.onlinelibrarysystem.exception.custom;

import com.libraryquerypie.onlinelibrarysystem.exception.ErrorCode;
import lombok.Getter;

@Getter
public class BookConflictException extends BaseException {

    private String isbn;

    public BookConflictException(String isbn) {
        super(ErrorCode.BOOK_BORROW_CONFLICT);
        this.isbn = isbn;
    }
}
