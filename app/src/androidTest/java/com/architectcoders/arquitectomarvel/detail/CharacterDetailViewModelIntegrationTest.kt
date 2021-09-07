package com.architectcoders.arquitectomarvel.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.architectcoders.arquitectomarvel.getOrAwaitValue
import com.architectcoders.arquitectomarvel.ui.detail.CharacterDetailViewModel
import com.architectcoders.arquitectomarvel.ui.detail.GetComicsInteractor
import com.architectcoders.arquitectomarvel.ui.detail.Resource
import com.architectcoders.usecases.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import mockedCharacter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CharacterDetailViewModelIntegrationTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Inject
    lateinit var getComicsForCharacter: GetComicsForCharacter

    @Inject
    lateinit var getLocalCharacterById: GetLocalCharacterById

    @Inject
    lateinit var isLocalCharacterFavorite: IsLocalCharacterFavorite

    @Inject
    lateinit var insertLocalFavoriteCharacter: InsertLocalFavoriteCharacter

    @Inject
    lateinit var deleteLocalFavoriteCharacter: DeleteLocalFavoriteCharacter

    @Inject
    lateinit var getComicsInteractor: GetComicsInteractor

    private lateinit var characterDetailViewModel: CharacterDetailViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        characterDetailViewModel = CharacterDetailViewModel(
            Dispatchers.Unconfined,
            mockedCharacter.id,
            getLocalCharacterById,
            isLocalCharacterFavorite,
            insertLocalFavoriteCharacter,
            deleteLocalFavoriteCharacter,
            getComicsInteractor
        )
    }

    @Test
    fun observingComicResource_retrievesComics() {
        val result = characterDetailViewModel.comicResource.getOrAwaitValue()
        assert(result is Resource.Loading)
    }

    @Test
    fun confirmIfUiModelState_isSetUiDetails() {
        assert(characterDetailViewModel.uiModel.value is CharacterDetailViewModel.UiModel.SetUiDetails)
    }
}
