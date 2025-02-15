package com.libraryquerypie.onlinelibrarysystem.borrow;

import com.libraryquerypie.onlinelibrarysystem.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    @Query("select borrow from Borrow borrow where borrow.book.id = :bookId and borrow.borrowStatus = 'BORROW'")
    List<Borrow> findBorrowByBookId(@Param("bookId") Long bookId);
}
