package main

import utils.CoroutineDispatchersTestImpl
import androidx.paging.ExperimentalPagingApi
import app.cash.turbine.test
import com.architectcoders.arquitectomarvel.ui.main.MainViewModel
import com.architectcoders.arquitectomarvel.ui.main.pagination.CharacterRemoteMediator
import com.architectcoders.usecases.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.time.ExperimentalTime

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @ExperimentalPagingApi
    @Mock
    private lateinit var characterRemoteMediator: CharacterRemoteMediator

    @Mock
    private lateinit var getPagingSourceFromCharacterEntity: GetPagingSourceFromCharacterEntity

    @Mock
    private lateinit var isPasswordAlreadyStored: IsPasswordAlreadyStored

    @Mock
    private lateinit var saveCredentials: SaveCredentials

    @Mock
    private lateinit var deleteCredentials: DeleteCredentials

    @Mock
    private lateinit var isPasswordCorrect: IsPasswordCorrect

    @Mock
    private lateinit var isRecoveryHintCorrect: IsRecoveryHintCorrect

    @Mock
    private lateinit var deleteAllLocalFavoriteCharacter: DeleteAllLocalFavoriteCharacter

    @Mock
    private lateinit var listener: (Boolean) -> Unit

    private lateinit var mainViewModel: MainViewModel

    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    @Before
    fun setup() {
        mainViewModel = MainViewModel(
            CoroutineDispatchersTestImpl(),
            characterRemoteMediator,
            getPagingSourceFromCharacterEntity,
            isPasswordAlreadyStored,
            saveCredentials,
            deleteCredentials,
            isPasswordCorrect,
            isRecoveryHintCorrect,
            deleteAllLocalFavoriteCharacter
        )
    }

    @ExperimentalTime
    @Test
    fun `confirm if the first passwordState state is the INITIAL_STATE value`(): Unit =
        runBlocking {
            mainViewModel.passwordState.test {
                assertEquals(awaitItem(), MainViewModel.PasswordState.INITIAL_STATE)
                cancelAndConsumeRemainingEvents()
            }
        }

    @ExperimentalTime
    @Test
    fun `confirm if the passwordState state has been updated`(): Unit = runBlocking {
        mainViewModel.updatePasswordState(MainViewModel.PasswordState.SUCCESSFUL)
        mainViewModel.passwordState.test {
            assertEquals(awaitItem(), MainViewModel.PasswordState.SUCCESSFUL)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `confirm if password is already stored`(): Unit = runBlocking {
        whenever(isPasswordAlreadyStored.invoke(Unit)).thenReturn(true)
        mainViewModel.ifDeviceNeitherHaveBiometricLoginNorPassword {
            launch {
                assert(it)
            }
        }
    }

    @Test
    fun `confirm if password is correct`(): Unit = runBlocking {
        whenever(isPasswordCorrect.invoke("password")).thenReturn(true)
        mainViewModel.checkIfPasswordIsCorrect("password", listener)
        argumentCaptor<Boolean>().apply {
            verify(listener).invoke(capture())
            assertEquals(true, firstValue)
        }
        verify(isPasswordCorrect).invoke("password")
    }

    @Test
    fun `confirm if hint is correct`(): Unit = runBlocking {
        whenever(isRecoveryHintCorrect.invoke("hint")).thenReturn(true)
        mainViewModel.checkIfHintIsCorrect("hint", listener)
        argumentCaptor<Boolean>().apply {
            verify(listener).invoke(capture())
            assertEquals(true, firstValue)
        }
        verify(isRecoveryHintCorrect).invoke("hint")
    }

    @Test
    fun `verify if deleteAllLocalFavoriteCharacter is invoked`(): Unit = runBlocking {
        whenever(deleteAllLocalFavoriteCharacter.invoke(Unit)).thenReturn(Unit)
        mainViewModel.resetLocalFavoriteCharacters()
        verify(deleteAllLocalFavoriteCharacter).invoke(Unit)
    }
}
