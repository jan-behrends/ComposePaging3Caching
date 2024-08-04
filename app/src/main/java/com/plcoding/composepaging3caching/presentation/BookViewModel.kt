package com.plcoding.composepaging3caching.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.plcoding.composepaging3caching.data.local.BookEntity
import com.plcoding.composepaging3caching.data.mappers.toBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    pager: Pager<Int, BookEntity>
) : ViewModel() {

    val bookPagingFlow = pager.flow.map { pagingData ->
        pagingData.map {
            it.toBook()
        }
    }.cachedIn(viewModelScope)

}