package com.libraryquerypie.onlinelibrarysystem.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    WRONG_LOGIN_INPUT(HttpStatus.BAD_REQUEST, "U-001", "로그인 시도 중 문제 발생."),
    WRONG_SIGNUP(HttpStatus.BAD_REQUEST, "U-002", "잘못된 회원가입 시도."),
    REQUEST_DTO_ERROR(HttpStatus.BAD_REQUEST, "G-001", "입력 필드에 잘못된 값."),
    AUTH_ERROR(HttpStatus.UNAUTHORIZED, "G-002", "인증되지 않은 사용자."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U-003", "사용자를 찾을 수 없음."),
    ISBN_DUPLICATION(HttpStatus.CONFLICT, "B-001", "중복된 ISBN."),
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "B-002", "책을 찾을 수 없음."),
    BOOK_ALREADY_BORROWED(HttpStatus.CONFLICT, "B-003", "이미 대출 중인 책."),
    ALREADY_RETURNED(HttpStatus.CONFLICT, "B-004", "이미 반납된 책."),
    BOOK_BORROW_CONFLICT(HttpStatus.CONFLICT, "B-005", "대출 중인 책은 삭제 할수 없습니다.");

    private HttpStatus status;
    private String code;
    private String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
