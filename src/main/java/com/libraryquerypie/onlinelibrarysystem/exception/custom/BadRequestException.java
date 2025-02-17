package com.libraryquerypie.onlinelibrarysystem.exception.custom;

import com.libraryquerypie.onlinelibrarysystem.exception.ErrorCode;
import lombok.Getter;

@Getter
public class BadRequestException extends BaseException {

    private String detailMessage;

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BadRequestException(ErrorCode errorCode, String detailMessage) {
        super(errorCode);
        this.detailMessage = detailMessage;
    }
}
