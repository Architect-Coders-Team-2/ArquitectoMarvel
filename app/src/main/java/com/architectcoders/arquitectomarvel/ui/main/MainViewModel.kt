package com.architectcoders.arquitectomarvel.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.arquitectomarvel.model.Repository
import com.architectcoders.arquitectomarvel.model.characters.Result
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _remoteData = MutableLiveData<List<Result>>()
    val remoteData: LiveData<List<Result>> get() = _remoteData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            try {
                _loading.value = true
                val characters = repository.getCharactersRemote()
                val resultsRemote = characters.characterData?.results ?: emptyList()
                _loading.value = false
                _remoteData.value = resultsRemote
            } catch (e: UnknownHostException) {
                Timber.e("qq_MainPresenter.onCreate: $e")
                _errorMessage.value = "No connection. Error DB"
            }
        }
    }
}
