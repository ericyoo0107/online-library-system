package com.libraryquerypie.onlinelibrarysystem.book;

import com.libraryquerypie.onlinelibrarysystem.book.dto.request.BookSearchRequest;
import com.libraryquerypie.onlinelibrarysystem.book.dto.BookSortBy;
import com.libraryquerypie.onlinelibrarysystem.entity.Book;
import com.libraryquerypie.onlinelibrarysystem.entity.QBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QBook qBook = QBook.book;

    @Override
    public Page<Book> searchBook(BookSearchRequest request, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        if (request.getTitle() != null) {
            builder.or(containsTitle(request.getTitle()));
        }
        if (request.getAuthor() != null) {
            builder.or(containsAuthor(request.getAuthor()));
        }
        if (request.getTag() != null) {
            builder.or(containsTag(request.getTag()));
        }
        List<Book> books = queryFactory.selectFrom(qBook)
                .where(builder)
                .orderBy(getSortOrder(request.getSortby()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(qBook)
                .where(builder)
                .fetchCount();

        return new PageImpl<>(books, pageable, total);
    }

    private BooleanExpression containsTitle(String title) {
        return title != null ? qBook.title.contains(title) : null;
    }

    private BooleanExpression containsAuthor(String author) {
        return author != null ? qBook.author.contains(author) : null;
    }

    private BooleanExpression containsTag(String tag) {
        return tag != null ? qBook.tag.contains(tag) : null;
    }

    private OrderSpecifier<?> getSortOrder(BookSortBy bookSortBy) {
        if (bookSortBy.equals(BookSortBy.TITLE)) {
            return qBook.title.asc();
        } else if (bookSortBy.equals(BookSortBy.PUBLISH_DATE)) {
            return qBook.publishDate.asc();
        }
        return qBook.id.asc();
    }
}
