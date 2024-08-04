package com.plcoding.composepaging3caching.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.plcoding.composepaging3caching.data.local.BookDatabase
import com.plcoding.composepaging3caching.data.local.BookEntity
import com.plcoding.composepaging3caching.data.remote.BookApi
import com.plcoding.composepaging3caching.data.remote.BookRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBookDatabase(@ApplicationContext context: Context): BookDatabase {
        return Room.databaseBuilder(
            context,
            BookDatabase::class.java,
            "books.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookApi(): BookApi {
        return Retrofit.Builder().baseUrl(BookApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideBookPager(bookDB: BookDatabase, bookApi: BookApi): Pager<Int, BookEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = BookRemoteMediator(
                bookDB = bookDB,
                bookApi = bookApi
            ),
            pagingSourceFactory = {
                bookDB.dao.pagingSoruce()
            }
        )
    }
}