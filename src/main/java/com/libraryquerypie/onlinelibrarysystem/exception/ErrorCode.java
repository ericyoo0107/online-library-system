package com.libraryquerypie.onlinelibrarysystem.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    WRONG_LOGIN_INPUT(HttpStatus.BAD_REQUEST, "U-001", "로그인 시도 중 문제 발생."),
    REQUEST_DTO_ERROR(HttpStatus.BAD_REQUEST, "G-001", "입력 필드에 잘못된 값.");

    private HttpStatus status;
    private String code;
    private String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
