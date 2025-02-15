package com.libraryquerypie.onlinelibrarysystem.exception.custom;

import com.libraryquerypie.onlinelibrarysystem.exception.ErrorCode;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String detailInfo) {
        super(ErrorCode.USER_NOT_FOUND, detailInfo);
    }
}
