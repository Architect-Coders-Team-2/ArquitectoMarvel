package com.architectcoders.arquitectomarvel.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.architectcoders.arquitectomarvel.model.Repository
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.model.database.dbItemComics
import com.architectcoders.arquitectomarvel.model.database.dbObject
import com.architectcoders.arquitectomarvel.model.database.relations.ResultWithItemsComics
import com.architectcoders.arquitectomarvel.model.database.relations.toListResult
import com.architectcoders.arquitectomarvel.ui.common.Scope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException

class MainViewModel(private val repository: Repository) : ViewModel(), Scope by Scope.Impl() {

    sealed class UiModel {
        object Loading : UiModel()
        class GetRemoteData(val results: List<Result>) : UiModel()
        class UpdateLocalData(val results: List<Result>) : UiModel()
        class GetErrorMessage(val message: String) : UiModel()
        class Navigation(val result: Result) : UiModel()
    }

    private val dao by lazy { repository.dao }
    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    init {
        initScope()
    }

    private fun refresh() {


        launch {
            try {
                _model.value = UiModel.Loading
                val characters = repository.getCharactersRemote()
                val resultsRemote = characters.data?.results ?: emptyList()
                _model.value = UiModel.GetRemoteData(resultsRemote)

                resultsRemote.forEach { result ->
                    dao.insertResult(result.dbObject)
                    val colectionUri = result.comics.collectionURI
                    result.comics.items.forEach { item ->
                        dao.insertComics(item.dbItemComics(colectionUri))
                    }
                }


            } catch (e: UnknownHostException) {
                Timber.e("qq_MainPresenter.onCreate: $e")
                _model.value = UiModel.GetErrorMessage("No connection. Error DB")
            }

            val listaLocal = mutableListOf<ResultWithItemsComics>()
            val tmpResult = dao.getResults()
            tmpResult.forEach {
                val itemForListaLocal = dao.getResultWithItemsComics(it.comicsCollectionURI)
                listaLocal.addAll(itemForListaLocal)
            }
            _model.value = UiModel.UpdateLocalData(listaLocal.toListResult)

        }
    }

    fun onResultClick(result: Result) {
        _model.value = UiModel.Navigation(result)
    }

    override fun onCleared() {
        cancelScope()
    }


}