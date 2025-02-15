package com.libraryquerypie.onlinelibrarysystem.exception;

import com.libraryquerypie.onlinelibrarysystem.exception.custom.BadRequestException;
import com.libraryquerypie.onlinelibrarysystem.exception.custom.DuplicateIsbnException;
import com.libraryquerypie.onlinelibrarysystem.exception.custom.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity badRequestExHandler(BadRequestException ex) {
        String messageExtra = ex.getDetailMessage();
        ErrorResponse errorResponseDto = ErrorResponse.of(ex.getErrorCode(), messageExtra);
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponseDto);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity notFoundExHandler(NotFoundException ex) {
        String messageExtra = ex.getDetailInfo();
        ErrorResponse errorResponseDto = ErrorResponse.of(ex.getErrorCode(), messageExtra);
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponseDto);
    }

    @ExceptionHandler(DuplicateIsbnException.class)
    public ResponseEntity duplicateIsbnExHandler(DuplicateIsbnException ex) {
        String messageExtra = ex.getIsbn();
        ErrorResponse errorResponseDto = ErrorResponse.of(ex.getErrorCode(), messageExtra);
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponseDto);
    }

    /**
     * DTO의 request가 컨트롤러 @Valid에서 빈 검증 통과 못한 경우  (G-001)
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity methodArgumentNotValidExHandler(MethodArgumentNotValidException ex) {
        Map<String, List<String>> messageExtra = new HashMap<>();
        convertBindingResultToMap(ex.getBindingResult(), messageExtra);
        ErrorResponse errorResponseDto = ErrorResponse.of(ErrorCode.REQUEST_DTO_ERROR, messageExtra);
        return ResponseEntity.status(BAD_REQUEST).body(errorResponseDto);
    }

    private void convertBindingResultToMap(BindingResult ex, Map<String, List<String>> messageExtra) {
        if (ex.hasErrors()) {
            ex.getFieldErrors()
                    .forEach(fieldError ->
                            messageExtra.computeIfAbsent(fieldError.getField(), key -> new ArrayList<>())
                                    .add(fieldError.getDefaultMessage()));
        }
    }
}
