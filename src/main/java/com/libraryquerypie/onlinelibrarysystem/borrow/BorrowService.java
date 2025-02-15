package com.libraryquerypie.onlinelibrarysystem.borrow;

import com.libraryquerypie.onlinelibrarysystem.book.BookRepository;
import com.libraryquerypie.onlinelibrarysystem.borrow.dto.BookBorrowRequest;
import com.libraryquerypie.onlinelibrarysystem.entity.Book;
import com.libraryquerypie.onlinelibrarysystem.entity.Borrow;
import com.libraryquerypie.onlinelibrarysystem.entity.User;
import com.libraryquerypie.onlinelibrarysystem.enums.BorrowStatus;
import com.libraryquerypie.onlinelibrarysystem.exception.ErrorCode;
import com.libraryquerypie.onlinelibrarysystem.exception.custom.AlreadyBorrowException;
import com.libraryquerypie.onlinelibrarysystem.exception.custom.NotFoundException;
import com.libraryquerypie.onlinelibrarysystem.exception.custom.UserNotFoundException;
import com.libraryquerypie.onlinelibrarysystem.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.AlreadyBoundException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Transactional
    public Long registerBorrow(String emailId, BookBorrowRequest request) {
        boolean isNotBorrowing = borrowRepository.findBorrowByBookId(request.getBookId()).isEmpty();
        if (!isNotBorrowing) {
            throw new AlreadyBorrowException("bookId : " + request.getBookId().toString());
        }
        User user = userRepository.findByEmailId(emailId)
                .orElseThrow(() -> new UserNotFoundException(emailId));
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOOK_NOT_FOUND, request.getBookId().toString()));
        Borrow borrow = borrowRepository.save(Borrow.borrowBook(request.getReturnDate(), user, book));
        return borrow.getId();
    }
}
