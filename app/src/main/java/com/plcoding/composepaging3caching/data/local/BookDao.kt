package com.plcoding.composepaging3caching.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface BookDao {

    @Upsert
    suspend fun upsertAll(books: List<BookEntity>)

    @Query("Select * FROM bookentity")
    fun pagingSoruce(): PagingSource<Int, BookEntity>

    @Query("DELETE FROM BookEntity")
    suspend fun clearAll()
}