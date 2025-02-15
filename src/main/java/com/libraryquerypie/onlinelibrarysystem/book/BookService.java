package com.libraryquerypie.onlinelibrarysystem.book;

import com.libraryquerypie.onlinelibrarysystem.book.dto.request.BookCreateRequest;
import com.libraryquerypie.onlinelibrarysystem.book.dto.request.BookSearchRequest;
import com.libraryquerypie.onlinelibrarysystem.book.dto.request.BookUpdateRequest;
import com.libraryquerypie.onlinelibrarysystem.book.dto.response.BookSearchResponse;
import com.libraryquerypie.onlinelibrarysystem.book.dto.response.PageBookResponse;
import com.libraryquerypie.onlinelibrarysystem.entity.Book;
import com.libraryquerypie.onlinelibrarysystem.exception.ErrorCode;
import com.libraryquerypie.onlinelibrarysystem.exception.custom.DuplicateIsbnException;
import com.libraryquerypie.onlinelibrarysystem.exception.custom.NotFoundException;
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

    @Transactional
    public BookSearchResponse updateBook(Long bookId, BookUpdateRequest request) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOOK_NOT_FOUND, "존재하지 않는 도서 id: " + bookId));
        Book updatedBook = bookRepository.save(book.update(request));
        log.info("{} 도서 정보 수정 성공", book.getTitle());
        return BookSearchResponse.fromEntity(updatedBook);
    }
}
