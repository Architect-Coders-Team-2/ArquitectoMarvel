package com.architectcoders.arquitectomarvel.integration

import androidx.paging.ExperimentalPagingApi
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.architectcoders.arquitectomarvel.ui.main.MainViewModel
import com.architectcoders.arquitectomarvel.ui.main.pagination.CharacterRemoteMediator
import com.architectcoders.usecases.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalPagingApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainViewModelIntegrationTest {

    @get:Rule
    val rule = HiltAndroidRule(this)

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
        rule.inject()
        mainViewModel = MainViewModel(
            Dispatchers.Unconfined,
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
    fun ifUpdatePasswordStateIsCalled_checkPasswordStateValue() {
        mainViewModel.updatePasswordState(MainViewModel.PasswordState.SUCCESSFUL)
        assertEquals(mainViewModel.passwordState.value, MainViewModel.PasswordState.SUCCESSFUL)
    }

    @Test
    fun ifCredentialsAreStored_checkIfPasswordIsCorrect() {
        mainViewModel.ifDeviceNeitherHaveBiometricLoginNorPassword {
            mainViewModel.checkIfPasswordIsCorrect("pwd") { isCorrect ->
                assertTrue(isCorrect)
            }
        }
    }

    @Test
    fun ifCredentialsAreStored_checkIfHintIsCorrect() {
        mainViewModel.ifDeviceNeitherHaveBiometricLoginNorPassword {
            mainViewModel.checkIfHintIsCorrect("hint") { isCorrect ->
                assertTrue(isCorrect)
            }
        }
    }

    @Test
    fun ifSaveCredentials_checkIfPasswordIsCorrect() {
        mainViewModel.saveCredentials("myPassword", "myHint")
        mainViewModel.checkIfPasswordIsCorrect("myPassword") {
            assertTrue(it)
        }
    }

    @Test
    fun ifSaveCredentials_checkIfHintIsCorrect() {
        mainViewModel.saveCredentials("myPassword", "myHint")
        mainViewModel.checkIfHintIsCorrect("myHint") {
            assertTrue(it)
        }
    }

    @Test
    fun ifDeleteCredentials_checkIfPasswordIsEmpty() {
        mainViewModel.deleteCredentials()
        mainViewModel.checkIfPasswordIsCorrect("") {
            assertTrue(it)
        }
    }

    @Test
    fun ifDeleteCredentials_checkIfHintIsEmpty() {
        mainViewModel.deleteCredentials()
        mainViewModel.checkIfHintIsCorrect("") {
            assertTrue(it)
        }
    }
}
