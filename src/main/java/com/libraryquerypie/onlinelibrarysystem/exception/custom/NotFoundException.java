package com.libraryquerypie.onlinelibrarysystem.exception.custom;

import com.libraryquerypie.onlinelibrarysystem.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundException extends BaseException {

    private String detailInfo;

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotFoundException(ErrorCode errorCode, String detailInfo) {
        super(errorCode);
        this.detailInfo = detailInfo;
    }
}
