package com.libraryquerypie.onlinelibrarysystem.entity;

import com.libraryquerypie.onlinelibrarysystem.enums.BorrowStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "borrows")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Borrow extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrow_id")
    private Long id;

    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "borrow_status", nullable = false)
    private BorrowStatus borrowStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Builder
    public Borrow(LocalDate returnDate, BorrowStatus borrowStatus, User user, Book book) {
        this.returnDate = returnDate;
        this.borrowStatus = borrowStatus;
        this.user = user;
        this.book = book;
    }

    public static Borrow borrowBook(LocalDate returnDate, User user, Book book) {
        return Borrow.builder()
                .returnDate(returnDate)
                .borrowStatus(BorrowStatus.BORROW)
                .user(user)
                .book(book)
                .build();
    }
}
