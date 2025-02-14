package com.libraryquerypie.onlinelibrarysystem.book;

import com.libraryquerypie.onlinelibrarysystem.book.dto.BookSearchRequest;
import com.libraryquerypie.onlinelibrarysystem.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {
    Page<Book> searchBook(BookSearchRequest request, Pageable pageable);
}
