package com.libraryquerypie.onlinelibrarysystem.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private String errorCode;
    private String message;
    private Object detail;

    @Builder
    public ErrorResponse(String errorCode, String message, Object detail) {
        this.errorCode = errorCode;
        this.message = message;
        this.detail = detail;
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

    public static ErrorResponse of(ErrorCode errorCode, Object detail) {
        return ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .message(errorCode.getMessage())
                .detail(detail)
                .build();
    }
}
