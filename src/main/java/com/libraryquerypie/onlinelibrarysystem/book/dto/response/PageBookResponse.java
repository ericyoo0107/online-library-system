package com.libraryquerypie.onlinelibrarysystem.book.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class PageBookResponse {
    @Schema(description = "도서 목록")
    private List<BookSearchResponse> content;
    @Schema(description = "현재 페이지 번호")
    private int pageNumber;
    @Schema(description = "페이지 크기")
    private int pageSize;
    @Schema(description = "전체 요소 수")
    private long totalElements;
    @Schema(description = "전체 페이지 수")
    private int totalPages;
    @Schema(description = "마지막 페이지 여부")
    private boolean last;

    @Builder
    public PageBookResponse(List content, int pageNumber, int pageSize, long totalElements, int totalPages, boolean last) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    public static PageBookResponse fromPage(Page<BookSearchResponse> page) {
        return PageBookResponse.builder()
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
