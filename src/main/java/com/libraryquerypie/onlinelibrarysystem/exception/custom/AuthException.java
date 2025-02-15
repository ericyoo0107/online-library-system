package com.libraryquerypie.onlinelibrarysystem.exception.custom;

import com.libraryquerypie.onlinelibrarysystem.exception.ErrorCode;

public class AuthException extends BaseException {

    public AuthException() {
        super(ErrorCode.AUTH_ERROR);
    }
}
