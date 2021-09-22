package com.architectcoders.arquitectomarvel.integration

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.architectcoders.arquitectomarvel.ui.favorite.FavoriteCharacterViewModel
import com.architectcoders.usecases.GetLocalFavoriteCharacters
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class FavoriteCharacterViewModelIntegrationTest {

    @get:Rule
    val rule = HiltAndroidRule(this)

    @Inject
    lateinit var getLocalFavoriteCharacters: GetLocalFavoriteCharacters

    private lateinit var favoriteCharacterViewModel: FavoriteCharacterViewModel

    @Before
    fun setUp() {
        rule.inject()
        favoriteCharacterViewModel = FavoriteCharacterViewModel(
            Dispatchers.Unconfined,
            getLocalFavoriteCharacters
        )
    }

    @Test
    fun confirmIfUiModelState_isRetrieveFavoriteCharacters() {
        assert(
            favoriteCharacterViewModel.uiModel.value is FavoriteCharacterViewModel.UiModel.RetrieveFavoriteCharacters
        )
    }
}
