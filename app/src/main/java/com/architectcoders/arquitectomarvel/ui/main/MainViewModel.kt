package com.architectcoders.arquitectomarvel.ui.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.architectcoders.arquitectomarvel.model.Repository
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.model.database.dbItemComics
import com.architectcoders.arquitectomarvel.model.database.dbObject
import com.architectcoders.arquitectomarvel.model.database.relations.ResultWithItemsComics
import com.architectcoders.arquitectomarvel.ui.common.Event
import com.architectcoders.arquitectomarvel.ui.common.Scope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException

class MainViewModel(private val repository: Repository) : ViewModel(), Scope by Scope.Impl() {

    sealed class UiModel {
        object Loading : UiModel()
        class SetRemoteData(val results: List<Result>) : UiModel()
        class GetErrorMessage(val message: String) : UiModel()
    }

    private val dao by lazy { repository.dao }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    private val _navigation = MutableLiveData<Event<Result>>()
    val navigation: LiveData<Event<Result>> = _navigation

    private val _viewItem = MutableLiveData<View>()
    val viewItem: LiveData<View> = _viewItem

    init {
        initScope()
    }

    private fun refresh() {
        launch {
            try {
                _model.value = UiModel.Loading
                val characters = repository.getCharactersRemote()
                val resultsRemote = characters.characterData?.results ?: emptyList()
                _model.value = UiModel.SetRemoteData(resultsRemote)

                // TODO Should we store data from the main list?
               /* resultsRemote.forEach { result ->
                    dao.insertResult(result.dbObject)
                    val collectionUri = result.comics.collectionURI
                    result.comics.items.forEach { item ->
                        dao.insertComics(item.dbItemComics(collectionUri))
                    }
                }*/
            } catch (e: UnknownHostException) {
                Timber.e("qq_MainPresenter.onCreate: $e")
                _model.value = UiModel.GetErrorMessage("No connection. Error DB")
            }

            // TODO Should we store data from the main list?
           /* val localList = mutableListOf<ResultWithItemsComics>()
            val tmpResult = dao.getResults()
            tmpResult.forEach {
                val itemForLocalList = dao.getResultWithItemsComics(it.comicsCollectionURI)
                localList.addAll(itemForLocalList)
            }*/
        }
    }

    fun onResultClick(result: Result, view: View) {
        _viewItem.value = view
        _navigation.value = Event(result)
    }

    override fun onCleared() {
        cancelScope()
    }
}
