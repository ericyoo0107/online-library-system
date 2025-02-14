package com.libraryquerypie.onlinelibrarysystem.book;

import com.libraryquerypie.onlinelibrarysystem.book.dto.BookCreateRequest;
import com.libraryquerypie.onlinelibrarysystem.book.dto.BookSearchRequest;
import com.libraryquerypie.onlinelibrarysystem.book.dto.BookSearchResponse;
import com.libraryquerypie.onlinelibrarysystem.book.dto.PageBookResponse;
import com.libraryquerypie.onlinelibrarysystem.entity.Book;
import com.libraryquerypie.onlinelibrarysystem.exception.custom.DuplicateIsbnException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Long registerBook(BookCreateRequest bookRequest) {
        if (bookRepository.existsByISBN(bookRequest.getIsbn())) {
            throw new DuplicateIsbnException("ISBN: " + bookRequest.getIsbn());
        }
        Book book = bookRepository.save(BookCreateRequest.toEntity(bookRequest));
        log.info("{} 도서 등록 성공", bookRequest.getTitle());
        return book.getId();
    }

    public PageBookResponse searchBook(BookSearchRequest request, Pageable pageable) {
        Page<Book> books = bookRepository.searchBook(request, pageable);
        Page<BookSearchResponse> response = books.map(BookSearchResponse::fromEntity);
        return PageBookResponse.builder()
                .content(response.getContent())
                .pageNumber(response.getNumber())
                .pageSize(response.getSize())
                .totalElements(response.getTotalElements())
                .totalPages(response.getTotalPages())
                .last(response.isLast())
                .build();
    }
}
