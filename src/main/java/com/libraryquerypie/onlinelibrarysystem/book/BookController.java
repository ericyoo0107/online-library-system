package com.libraryquerypie.onlinelibrarysystem.book;

import com.libraryquerypie.onlinelibrarysystem.book.dto.request.BookCreateRequest;
import com.libraryquerypie.onlinelibrarysystem.book.dto.request.BookSearchRequest;
import com.libraryquerypie.onlinelibrarysystem.book.dto.response.PageBookResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
@Tag(name = "Book API", description = "도서 관리 관련 API")
public class BookController {

    private final BookService bookService;

    @PostMapping("/register")
    @Operation(summary = "도서 등록", description = "새로운 도서를 추가 하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "새로운 도서 등록 성공",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "409", description = "중복된 ISBN",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<String> registerBook(@RequestBody @Valid BookCreateRequest bookRequest) {
        Long bookId = bookService.registerBook(bookRequest);
        return ResponseEntity.created(URI.create("/book/" + bookId)).body("도서 등록 성공");
    }

    @GetMapping("/list/find")
    @Operation(summary = "도서 검색", description = "도서를 검색하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도서 검색 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 값",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<PageBookResponse> searchBook(BookSearchRequest request, Pageable pageable) {
        PageBookResponse response = bookService.searchBook(request, pageable);
        return ResponseEntity.ok(response);
    }
}
