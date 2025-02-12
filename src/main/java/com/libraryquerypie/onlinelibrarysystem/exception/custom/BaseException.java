package com.libraryquerypie.onlinelibrarysystem.exception.custom;

import com.libraryquerypie.onlinelibrarysystem.exception.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private ErrorCode errorCode;

    public BaseException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
