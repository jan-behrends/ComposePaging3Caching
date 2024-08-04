package com.plcoding.composepaging3caching.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.plcoding.composepaging3caching.data.local.BookDatabase
import com.plcoding.composepaging3caching.data.local.BookEntity
import com.plcoding.composepaging3caching.data.mappers.toBookEntity
import kotlinx.coroutines.delay
import okio.IOException

@OptIn(ExperimentalPagingApi::class)
class BookRemoteMediator(
    private val bookDB: BookDatabase,
    private val bookApi: BookApi
) :
    RemoteMediator<Int, BookEntity>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BookEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }
            Log.d("Load key", loadKey.toString())
            delay(2000L)
            val books = bookApi.getBooks(
                page = loadKey,
                pageCount =  state.config.pageSize
            ).data

            bookDB.withTransaction {
                if(loadType == LoadType.REFRESH){
                    bookDB.dao.clearAll()
                }
                val bookEntities = books.map{ it.toBookEntity()}
                bookDB.dao.upsertAll(bookEntities)
            }
            return MediatorResult.Success(endOfPaginationReached = books.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}