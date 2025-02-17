package com.libraryquerypie.onlinelibrarysystem.book;

import com.libraryquerypie.onlinelibrarysystem.book.dto.BookSortBy;
import com.libraryquerypie.onlinelibrarysystem.book.dto.request.BookCreateRequest;
import com.libraryquerypie.onlinelibrarysystem.book.dto.request.BookSearchRequest;
import com.libraryquerypie.onlinelibrarysystem.book.dto.response.PageBookResponse;
import com.libraryquerypie.onlinelibrarysystem.entity.Book;
import com.libraryquerypie.onlinelibrarysystem.exception.custom.DuplicateIsbnException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional(readOnly = true)
public class BookControllerTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @WithMockUser
    @Transactional
    @DisplayName("도서를 등록한다.")
    void registerBook_Success() {
        // Given
        BookCreateRequest request = BookCreateRequest.builder()
                .isbn("9781234567890")
                .title("제목1")
                .author("작가1")
                .publishDate(LocalDate.now())
                .tag("태그")
                .build();

        // When
        Long bookId = bookService.registerBook(request);
        String isbn = bookRepository.findById(bookId).get().getISBN();

        // Then
        assertThat(isbn).isEqualTo("9781234567890");
        assertThat(bookRepository.existsByISBN(request.getIsbn())).isTrue();
    }

    @Test
    @WithMockUser
    @Transactional
    @DisplayName("중복된 ISBN으로 도서를 신규 등록하면 예외가 발생한다.")
    void registerBook_DuplicateIsbn() {
        // Given
        makeDummyBook();
        BookCreateRequest request = BookCreateRequest.builder()
                .isbn("9781234567890")
                .title("제목1")
                .author("작가1")
                .publishDate(LocalDate.now())
                .tag("태그")
                .build();

        // When & Then
        assertThatThrownBy(() -> bookService.registerBook(request))
                .isInstanceOf(DuplicateIsbnException.class);
    }

    @Test
    @DisplayName("로그인 하지 않은 사용자는 도서를 등록할 수 없다.")
    void registerBook_Unauthorized() {
        // Given
        BookCreateRequest request = BookCreateRequest.builder()
                .isbn("9781234567890")
                .title("제목1")
                .author("작가1")
                .publishDate(LocalDate.now())
                .tag("태그")
                .build();

        // When & Then
        assertThat(bookRepository.existsByISBN(request.getIsbn())).isFalse();
    }

    @Transactional
    void makeDummyBook() {
        Book book = Book.builder()
                .ISBN("9781234567890")
                .title("제목1")
                .author("작가1")
                .publishDate(LocalDate.now())
                .tag("태그")
                .build();
        bookRepository.save(book);
    }

    @Test
    @WithMockUser
    @DisplayName("도서를 검색한다.")
    void searchBook_Success() {
        // Given
        makeDummyBook();
        BookSearchRequest request = BookSearchRequest.builder()
                .sortby(BookSortBy.TITLE)
                .build();
        Pageable pageable = PageRequest.of(0, 10);

        // When
        PageBookResponse response = bookService.searchBook(request.getSort(), request, pageable);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getContent()).isNotEmpty();
    }
}
