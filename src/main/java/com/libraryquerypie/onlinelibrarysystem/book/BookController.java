package com.libraryquerypie.onlinelibrarysystem.book;

import com.libraryquerypie.onlinelibrarysystem.book.dto.request.BookCreateRequest;
import com.libraryquerypie.onlinelibrarysystem.book.dto.request.BookSearchRequest;
import com.libraryquerypie.onlinelibrarysystem.book.dto.request.BookUpdateRequest;
import com.libraryquerypie.onlinelibrarysystem.book.dto.response.BookSearchResponse;
import com.libraryquerypie.onlinelibrarysystem.book.dto.response.PageBookResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
@Tag(name = "Book API", description = "도서 관리 관련 API")
public class BookController {

    private final BookService bookService;

    @PostMapping("/register")
    @Operation(summary = "도서 등록", description = "새로운 도서를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "새로운 도서 등록 성공",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "회원 인가 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "409", description = "중복된 ISBN",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<String> registerBook(@RequestBody @Valid BookCreateRequest bookRequest) {
        Long bookId = bookService.registerBook(bookRequest);
        return ResponseEntity.created(URI.create("/book/" + bookId)).body("도서 등록 성공");
    }

    @GetMapping("/list/find")
    @Operation(summary = "도서 검색", description = "등록된 모든 도서를 조회하거나 특정 도서를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도서 검색 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<PageBookResponse> searchBook(BookSearchRequest request, Pageable pageable) {
        log.info("API 호출!");
        String sort = request.getSort();
        PageBookResponse response = bookService.searchBook(sort, request, pageable);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/update/{bookId}")
    @Operation(summary = "도서 수정", description = "도서 정보를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도서 수정 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "회원 인가 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "도서를 찾을 수 없음",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<BookSearchResponse> updateBook(
            @Parameter(name = "bookId", description = "도서의 고유 ID", required = true, example = "1") @PathVariable("bookId") Long bookId,
            @RequestBody @Valid BookUpdateRequest request) {
        BookSearchResponse response = bookService.updateBook(bookId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{bookId}")
    @Operation(summary = "도서 삭제", description = "도서 정보를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도서 삭제 성공",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "회원 인가 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "도서를 찾을 수 없음",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "409", description = "대출 중인 도서를 삭제 시도",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<String> deleteBook(
            @Parameter(name = "bookId", description = "도서의 고유 ID", required = true, example = "1") @PathVariable("bookId") Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok("도서 삭제 성공");
    }
}
