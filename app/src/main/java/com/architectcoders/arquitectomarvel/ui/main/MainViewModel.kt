package com.architectcoders.arquitectomarvel.ui.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.architectcoders.arquitectomarvel.data.ui_models.ResultUI
import com.architectcoders.arquitectomarvel.ui.common.Event
import com.architectcoders.arquitectomarvel.ui.common.PAGE_SIZE
import com.architectcoders.arquitectomarvel.ui.main.pagination.HerosPagingSource
import com.architectcoders.module.usescases.UseCaseGetCharactersRemote
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCaseGetCharactersRemote: UseCaseGetCharactersRemote) :
    ViewModel() {

    private val _navigation = MutableLiveData<Event<ResultUI>>()
    val navigation: LiveData<Event<ResultUI>> = _navigation

    private val _viewItem = MutableLiveData<View>()
    val viewItem: LiveData<View> = _viewItem

    val pager = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE
        ),
        pagingSourceFactory = { HerosPagingSource(useCaseGetCharactersRemote) }
    ).flow.cachedIn(viewModelScope)

    fun onResultClick(result: ResultUI, view: View) {
        _viewItem.value = view
        _navigation.value = Event(result)
    }
}
