package com.architectcoders.arquitectomarvel.ui.main

import androidx.paging.*
import com.architectcoders.arquitectomarvel.data.database.CharacterEntity
import com.architectcoders.arquitectomarvel.ui.common.REQUEST_LIMIT
import com.architectcoders.arquitectomarvel.ui.common.ScopeViewModel
import com.architectcoders.arquitectomarvel.ui.main.pagination.CharacterRemoteMediator
import com.architectcoders.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@ExperimentalPagingApi
@Inject constructor(
    coroutineDispatcher: CoroutineDispatcher,
    characterRemoteMediator: CharacterRemoteMediator,
    private val getPagingSourceFromCharacterEntity: GetPagingSourceFromCharacterEntity,
    private val isPasswordAlreadyStored: IsPasswordAlreadyStored,
    private val saveCredentials: SaveCredentials,
    private val deleteCredentials: DeleteCredentials,
    private val isPasswordCorrect: IsPasswordCorrect,
    private val isRecoveryHintCorrect: IsRecoveryHintCorrect,
    private val deleteAllLocalFavoriteCharacter: DeleteAllLocalFavoriteCharacter
) : ScopeViewModel(coroutineDispatcher) {
    @ExperimentalPagingApi
    val pager = Pager(
        config = PagingConfig(pageSize = REQUEST_LIMIT / 2),
        remoteMediator = characterRemoteMediator
    ) {
        getPagingSourceFromCharacterEntity.invoke() as PagingSource<Int, CharacterEntity>
    }.flow.cachedIn(this)

    private val _passwordState = MutableStateFlow(PasswordState.INITIAL_STATE)
    val passwordState = _passwordState.asStateFlow()

    enum class PasswordState {
        INITIAL_STATE, SAVE_PASSWORD, REQUEST_PASSWORD, REQUEST_HINT, SUCCESSFUL
    }

    fun updatePasswordState(passwordState: PasswordState) {
        _passwordState.value = passwordState
    }

    fun ifDeviceNeitherHaveBiometricLoginNorPassword(listener: (Boolean) -> Unit) {
        launch {
            listener(isPasswordAlreadyStored.invoke(Unit))
        }
    }

    fun saveCredentials(password: String, recoveryHint: String) {
        launch {
            saveCredentials.invoke(password, recoveryHint)
        }
    }

    fun deleteCredentials() {
        launch {
            deleteCredentials.invoke(Unit)
        }
    }

    fun checkIfPasswordIsCorrect(password: String, listener: (Boolean) -> Unit) {
        launch {
            listener(isPasswordCorrect.invoke(password))
        }
    }

    fun checkIfHintIsCorrect(hint: String, listener: (Boolean) -> Unit) {
        launch {
            listener(isRecoveryHintCorrect.invoke(hint))
        }
    }

    fun resetLocalFavoriteCharacters() {
        launch {
            deleteAllLocalFavoriteCharacter.invoke(Unit)
        }
    }
}
