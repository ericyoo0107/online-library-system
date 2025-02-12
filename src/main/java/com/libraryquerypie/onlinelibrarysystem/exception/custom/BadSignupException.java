package com.libraryquerypie.onlinelibrarysystem.exception.custom;

import com.libraryquerypie.onlinelibrarysystem.exception.ErrorCode;

public class BadSignupException extends BadRequestException {

    public BadSignupException(String detailMessage) {
        super(ErrorCode.WRONG_SIGNUP, detailMessage);
    }
}
