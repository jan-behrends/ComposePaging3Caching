package com.plcoding.composepaging3caching.data.remote

import retrofit2.http.Query
import retrofit2.http.GET

interface BookApi {

    @GET("books")
    suspend fun getBooks(
        @Query("page") page: Int,
        @Query("per_page") pageCount: Int
    ): BookResponse

    companion object{
        const val BASE_URL = "http://10.0.2.2:80/api/"
    }
}