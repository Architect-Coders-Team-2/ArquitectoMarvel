package com.architectcoders.arquitectomarvel.integration

import androidx.paging.ExperimentalPagingApi
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.architectcoders.arquitectomarvel.ui.common.CoroutineDispatchers
import com.architectcoders.arquitectomarvel.ui.main.MainViewModel
import com.architectcoders.arquitectomarvel.ui.main.pagination.CharacterRemoteMediator
import com.architectcoders.arquitectomarvel.utils.CoroutineDispatchersTestImpl
import com.architectcoders.usecases.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainViewModelIntegrationTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var coroutineDispatchersTestImpl: CoroutineDispatchersTestImpl

    @Inject
    lateinit var getPagingSourceFromCharacterEntity: GetPagingSourceFromCharacterEntity

    @Inject
    lateinit var isPasswordAlreadyStored: IsPasswordAlreadyStored

    @Inject
    lateinit var saveCredentials: SaveCredentials

    @Inject
    lateinit var deleteCredentials: DeleteCredentials

    @Inject
    lateinit var isPasswordCorrect: IsPasswordCorrect

    @Inject
    lateinit var isRecoveryHintCorrect: IsRecoveryHintCorrect

    @Inject
    lateinit var deleteAllLocalFavoriteCharacter: DeleteAllLocalFavoriteCharacter

    @Inject
    lateinit var characterRemoteMediator: CharacterRemoteMediator

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        mainViewModel = MainViewModel(
            coroutineDispatchersTestImpl,
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

    @Test
    fun callUpdatePasswordState_checkPasswordStateValue() {
        mainViewModel.updatePasswordState(MainViewModel.PasswordState.SUCCESSFUL)
        assertEquals(mainViewModel.passwordState.value, MainViewModel.PasswordState.SUCCESSFUL)
    }

    @Test
    fun saveCredentials_checkIfPasswordIsCorrect() {
        mainViewModel.saveCredentials("myPassword", "myHint")
        mainViewModel.ifDeviceNeitherHaveBiometricLoginNorPassword {
            mainViewModel.checkIfPasswordIsCorrect("myPassword") { isCorrect ->
                assertTrue(isCorrect)
            }
        }
    }

    @Test
    fun saveCredentials_checkIfHintIsCorrect() {
        mainViewModel.saveCredentials("myPassword", "myHint")
        mainViewModel.ifDeviceNeitherHaveBiometricLoginNorPassword {
            mainViewModel.checkIfHintIsCorrect("myHint") { isCorrect ->
                assertTrue(isCorrect)
            }
        }
    }

    @Test
    fun saveCredentials_deleteCredentials_checkIfPasswordIsDeleted() {
        mainViewModel.saveCredentials("myPassword", "myHint")
        mainViewModel.deleteCredentials()
        mainViewModel.checkIfPasswordIsCorrect("myPassword") {
            assertFalse(it)
        }
    }

    @Test
    fun saveCredentials_deleteCredentials_checkIfHintIsDeleted() {
        mainViewModel.saveCredentials("myPassword", "myHint")
        mainViewModel.deleteCredentials()
        mainViewModel.checkIfHintIsCorrect("myHint") {
            assertFalse(it)
        }
    }
}
