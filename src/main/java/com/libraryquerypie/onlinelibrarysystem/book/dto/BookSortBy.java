package com.libraryquerypie.onlinelibrarysystem.book.dto;

public enum BookSortBy {
    TITLE("title"),
    PUBLISH_DATE("publishDate");

    private final String value;

    BookSortBy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
