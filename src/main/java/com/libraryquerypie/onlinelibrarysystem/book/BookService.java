package com.libraryquerypie.onlinelibrarysystem.book;

import com.libraryquerypie.onlinelibrarysystem.book.dto.BookCreateRequest;
import com.libraryquerypie.onlinelibrarysystem.entity.Book;
import com.libraryquerypie.onlinelibrarysystem.exception.custom.DuplicateIsbnException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
