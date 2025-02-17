package com.libraryquerypie.onlinelibrarysystem.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

    @Schema(description = "에러 코드", example = "ISBN_DUPLICATION")
    private String errorCode;
    @Schema(description = "에러 메시지", example = "ISBN이 중복되었습니다.")
    private String message;
    @Schema(description = "에러 상세 정보", example = "중복된 ISBN: 1234567890")
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
