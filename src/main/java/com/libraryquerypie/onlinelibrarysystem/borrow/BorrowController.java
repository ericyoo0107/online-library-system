package com.libraryquerypie.onlinelibrarysystem.borrow;

import com.libraryquerypie.onlinelibrarysystem.borrow.dto.BookBorrowRequest;
import com.libraryquerypie.onlinelibrarysystem.enums.BorrowStatus;
import com.libraryquerypie.onlinelibrarysystem.jwt.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/borrow")
@Tag(name = "Borrow API", description = "대출 및 반납 관리 관련 API")
public class BorrowController {

    private final BorrowService borrowService;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    @Operation(summary = "대출 등록", description = "사용자가 도서를 대출 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "도서 대출 성공",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "회원 인가 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "사용자 또는 도서를 찾을 수 없음",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "409", description = "이미 대출 중인 도서",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<String> registerBorrow(
            @Parameter(description = "Bearer {JWT}", required = true, example = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoiZWVlQGdtYWlsLmNvbSIsImlhdCI6MTczOTYzMzUxMywiZXhwIjoxNzM5NzE5OTEzfQ.PVkQ-qZk43EElq-3dJ99Ur-zOMy5ujeTkAiYulIBnag")
            @RequestHeader("Authorization") String token, @RequestBody @Valid BookBorrowRequest request) {
        String jwt = token.substring(7);
        String emailId = jwtUtils.extractEmailId(jwt);
        Long borrowId = borrowService.registerBorrow(emailId, request);
        return ResponseEntity.created(URI.create("/borrow/" + borrowId)).body("도서 대출 성공");
    }

    @GetMapping("/check/{bookId}")
    @Operation(summary = "대출 상태 확인", description = "도서가 대출 중인지 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도서 대출 가능",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "도서를 찾을 수 없음",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<String> checkBorrow(
            @Parameter(name = "bookId", description = "도서의 고유 ID", required = true, example = "1")
                                                  @PathVariable("bookId") Long bookId) {
        String status = borrowService.checkBorrow(bookId);
        return ResponseEntity.ok(status);
    }

    @PatchMapping("/return/{bookId}")
    @Operation(summary = "도서 반납", description = "대출된 도서를 반납합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도서 반납 성공",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
            @ApiResponse(responseCode = "401", description = "회원 인가 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "도서를 찾을 수 없음",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "409", description = "이미 반납된 도서",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<String> returnBook(
            @Parameter(description = "Bearer {JWT}", required = true, example = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoiZWVlQGdtYWlsLmNvbSIsImlhdCI6MTczOTYzMzUxMywiZXhwIjoxNzM5NzE5OTEzfQ.PVkQ-qZk43EElq-3dJ99Ur-zOMy5ujeTkAiYulIBnag")
            @RequestHeader("Authorization") String token,
                                             @Parameter(name = "bookId", description = "도서의 고유 ID", required = true, example = "1")
                                             @PathVariable("bookId") Long bookId) {
        String jwt = token.substring(7);
        String emailId = jwtUtils.extractEmailId(jwt);
        borrowService.returnBook(emailId, bookId);
        return ResponseEntity.ok("도서 반납 성공");
    }

}
