package com.libraryquerypie.onlinelibrarysystem.borrow;

import com.libraryquerypie.onlinelibrarysystem.entity.Book;
import com.libraryquerypie.onlinelibrarysystem.entity.Borrow;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select borrow from Borrow borrow where borrow.book.id = :bookId and borrow.borrowStatus = 'BORROW'")
    List<Borrow> findBorrowByBookId(@Param("bookId") Long bookId);

    @Query("select b from Borrow b where b.user.emailId = :emailId and b.book.id = :bookId")
    List<Borrow> findBorrowByUserIdAndBookId(@Param("emailId") String emailId, @Param("bookId") Long bookId);

    List<Borrow> findByBook(Book book);

    void deleteAllByBook(Book book);
}
