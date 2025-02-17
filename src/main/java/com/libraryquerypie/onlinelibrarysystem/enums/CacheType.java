package com.libraryquerypie.onlinelibrarysystem.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheType {

    GET_BOOK("BOOK", 10, 10),
    GET_USER("USERS", 10, 10);

    private final String cacheName;
    private final int expiredAfterWrite; // hours
    private final int maximumSize;
}
