package com.libraryquerypie.onlinelibrarysystem.exception.custom;

import com.libraryquerypie.onlinelibrarysystem.exception.ErrorCode;

public class BadLoginException extends BadRequestException {

    public BadLoginException(String detailMessage) {
        super(ErrorCode.WRONG_LOGIN_INPUT, detailMessage);
    }
}
