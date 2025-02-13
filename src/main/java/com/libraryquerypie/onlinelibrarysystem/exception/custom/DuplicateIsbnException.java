package com.libraryquerypie.onlinelibrarysystem.exception.custom;

import com.libraryquerypie.onlinelibrarysystem.exception.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicateIsbnException extends BaseException {

    private String isbn;

    public DuplicateIsbnException(String isbn) {
        super(ErrorCode.ISBN_DUPLICATION);
        this.isbn = isbn;
    }
}
