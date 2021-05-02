package com.architectcoders.arquitectomarvel.ui.main

import android.view.View
import androidx.lifecycle.*
import androidx.paging.*
import com.architectcoders.arquitectomarvel.model.Repository
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.ui.common.Event
import timber.log.Timber

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _navigation = MutableLiveData<Event<Result>>()
    val navigation: LiveData<Event<Result>> = _navigation

    private val _viewItem = MutableLiveData<View>()
    val viewItem: LiveData<View> = _viewItem

    val pager = Pager(
        config = PagingConfig(
            pageSize = 18,
//            maxSize = 54,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { ResultPagingSource(repository) }
    ).flow.cachedIn(viewModelScope)

    fun onResultClick(result: Result, view: View) {
        Timber.d("qq_MainViewModel.onResultClick: ${result.description} (result.description)")
        _viewItem.value = view
        _navigation.value = Event(result)
    }
}
