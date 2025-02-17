package com.libraryquerypie.onlinelibrarysystem.book;

import com.libraryquerypie.onlinelibrarysystem.book.dto.request.BookCreateRequest;
import com.libraryquerypie.onlinelibrarysystem.entity.Book;
import com.libraryquerypie.onlinelibrarysystem.exception.custom.DuplicateIsbnException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ActiveProfiles("test")
@Transactional(readOnly = true)
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

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
        bookService.registerBook(request);
        String isbn = bookRepository.findAll().get(0).getISBN();

        // Then
        assertThat(isbn).isEqualTo("9781234567890");
        assertTrue(bookRepository.existsByISBN(request.getIsbn()));
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
        assertThatThrownBy(() -> {
            bookService.registerBook(request);
        })
                .isInstanceOf(DuplicateIsbnException.class);
    }

    @Test
    @Transactional
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
        assertFalse(bookRepository.existsByISBN(request.getIsbn()));
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
}