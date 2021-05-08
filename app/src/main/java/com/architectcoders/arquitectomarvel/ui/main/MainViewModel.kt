package com.architectcoders.arquitectomarvel.ui.main

import android.view.View
import androidx.lifecycle.*
import androidx.paging.*
import com.architectcoders.arquitectomarvel.model.mappers.ResultUI
import com.architectcoders.arquitectomarvel.ui.common.Event
import com.architectcoders.module.usescases.UseCaseGetCharactersRemote

class MainViewModel(private val useCaseGetCharactersRemote: UseCaseGetCharactersRemote) : ViewModel() {

    private val _navigation = MutableLiveData<Event<ResultUI>>()
    val navigation: LiveData<Event<ResultUI>> = _navigation

    private val _viewItem = MutableLiveData<View>()
    val viewItem: LiveData<View> = _viewItem

    val pager = Pager(
        config = PagingConfig(
            pageSize = 18
        ),
        pagingSourceFactory = { ResultUIPagingSource(useCaseGetCharactersRemote) }
    ).flow.cachedIn(viewModelScope)

    fun onResultClick(result: ResultUI, view: View) {
        _viewItem.value = view
        _navigation.value = Event(result)
    }
}

class VMFuseCaseGetCharactersRemote (val arg: UseCaseGetCharactersRemote): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return   modelClass.getConstructor(UseCaseGetCharactersRemote::class.java).newInstance(arg)
    }
}
