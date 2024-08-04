package com.plcoding.composepaging3caching.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
)
