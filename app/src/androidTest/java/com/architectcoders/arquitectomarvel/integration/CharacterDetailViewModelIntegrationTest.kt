package com.architectcoders.arquitectomarvel.integration

import androidx.lifecycle.asFlow
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.architectcoders.arquitectomarvel.data.database.ComicEntity
import com.architectcoders.arquitectomarvel.data.database.MarvelDao
import com.architectcoders.arquitectomarvel.data.database.toCharacterEntity
import com.architectcoders.arquitectomarvel.ui.common.CoroutineDispatchers
import com.architectcoders.arquitectomarvel.ui.detail.CharacterDetailViewModel
import com.architectcoders.arquitectomarvel.ui.detail.GetComicsInteractor
import com.architectcoders.arquitectomarvel.ui.detail.Resource
import com.architectcoders.arquitectomarvel.utils.CoroutineDispatchersTestImpl
import com.architectcoders.usecases.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
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

    @Inject
    lateinit var coroutineDispatchersTestImpl: CoroutineDispatchersTestImpl

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

    @Inject
    lateinit var marvelDao: MarvelDao

    private lateinit var characterDetailViewModel: CharacterDetailViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        characterDetailViewModel = CharacterDetailViewModel(
            coroutineDispatchersTestImpl,
            mockedCharacter.id,
            getLocalCharacterById,
            isLocalCharacterFavorite,
            insertLocalFavoriteCharacter,
            deleteLocalFavoriteCharacter,
            getComicsInteractor
        )
    }

    @Test
    fun observingComicResource_retrievesComics() = runBlocking {
        val result =
            characterDetailViewModel.comicResource.asFlow()
                .stateIn(
                    TestCoroutineScope(),
                    SharingStarted.WhileSubscribed(5000),
                    Resource.Loading()
                )
        assertTrue(result.value is Resource.Loading<List<ComicEntity>>)
    }

    @Test
    fun confirmIfUiModelState_isSetUiDetails() {
        runBlockingTest {
            marvelDao.insertAllLocalCharacters(listOf(mockedCharacter.toCharacterEntity))
        }
        assertTrue(characterDetailViewModel.uiModel.value is CharacterDetailViewModel.UiModel.SetUiDetails)
    }
}
