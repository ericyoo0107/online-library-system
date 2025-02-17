package com.libraryquerypie.onlinelibrarysystem.borrow;

import com.libraryquerypie.onlinelibrarysystem.book.BookRepository;
import com.libraryquerypie.onlinelibrarysystem.borrow.dto.BookBorrowRequest;
import com.libraryquerypie.onlinelibrarysystem.entity.Book;
import com.libraryquerypie.onlinelibrarysystem.entity.Borrow;
import com.libraryquerypie.onlinelibrarysystem.entity.User;
import com.libraryquerypie.onlinelibrarysystem.enums.Role;
import com.libraryquerypie.onlinelibrarysystem.exception.custom.AlreadyBorrowException;
import com.libraryquerypie.onlinelibrarysystem.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional(readOnly = true)
@ActiveProfiles("test")
public class BorrowServiceTest {

    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BorrowRepository borrowRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        bookRepository.deleteAll();
        borrowRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("동시에 여러 명이 접근해도 한 명만 책을 빌릴 수 있다.")
    public void testConcurrentBorrow() {
        // Given
        createDummyUser();
        createDummyBook();
        int threadCount = 10;
        BookBorrowRequest request = BookBorrowRequest.builder()
                .bookId(1L)
                .returnDate(LocalDate.of(2025, 12, 31))
                .build();
        AtomicInteger failCount = new AtomicInteger(0);
        String emailId = "ericyoo0107@naver.com";

        // When
        for (int i = 0; i < threadCount; i++) {
            try {
                borrowService.registerBorrow(emailId, request);
            } catch (AlreadyBorrowException e) {
                failCount.incrementAndGet();
            }
        }

        // Then
        List<Borrow> borrow = borrowRepository.findBorrowByUserIdAndBookId("ericyoo0107@naver.com", 1L);
        assertThat(failCount.get()).isEqualTo(threadCount - 1);
        assertThat(borrow.size()).isEqualTo(1);
    }

    @Transactional
    public void createDummyUser() {
        User user = User.builder()
                .emailId("ericyoo0107@naver.com")
                .hashPassword("password")
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }

    @Transactional
    public void createDummyBook() {
        Book book1 = Book.builder()
                .ISBN("9781234567890")
                .title("제목1")
                .author("작가1")
                .publishDate(LocalDate.now())
                .tag("태그1")
                .build();
        Book book2 = Book.builder()
                .ISBN("9781234567891")
                .title("제목2")
                .author("작가2")
                .publishDate(LocalDate.now())
                .tag("태그2")
                .build();
        bookRepository.saveAll(List.of(book1, book2));
    }
}
