package com.plcoding.composepaging3caching.data.mappers

import com.plcoding.composepaging3caching.data.local.BookEntity
import com.plcoding.composepaging3caching.data.remote.BookDto
import com.plcoding.composepaging3caching.domain.Book

fun BookDto.toBookEntity(): BookEntity {
    return BookEntity(
        id = id,
        name = name
    )
}

fun BookEntity.toBook(): Book {
    return Book(
        id = id,
        name = name
    )
}