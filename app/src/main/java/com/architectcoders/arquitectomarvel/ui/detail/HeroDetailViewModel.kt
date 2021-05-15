package com.architectcoders.arquitectomarvel.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.toCharacterResultDomain
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.comics.Result
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.comics.fromListResult
import com.architectcoders.arquitectomarvel.data.usescases_impl.UseCaseDeleteFavoriteCharacterImpl
import com.architectcoders.arquitectomarvel.data.usescases_impl.UseCaseGetComicsRemoteImpl
import com.architectcoders.arquitectomarvel.data.usescases_impl.UseCaseInsertFavoriteCharacterImpl
import com.architectcoders.arquitectomarvel.data.usescases_impl.UseCaseIsCharacterFavoriteImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.Result as CharacterResult

@HiltViewModel
class HeroDetailViewModel @Inject constructor(
    private val useCaseGetComicsRemoteImpl: UseCaseGetComicsRemoteImpl,
    private val useCaseInsertFavoriteCharacterImpl: UseCaseInsertFavoriteCharacterImpl,
    private val useCaseIsCharacterFavoriteImpl: UseCaseIsCharacterFavoriteImpl,
    private val useCaseDeleteFavoriteCharacterImpl: UseCaseDeleteFavoriteCharacterImpl,
) : ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class SetHeroDetails(
            val listener: (
                selectedHero: CharacterResult,
                isCharacterFavorite: Boolean,
            ) -> Unit, val onHeroShown: (Int) -> Unit
        ) : UiModel()

        class UpdateFAB(
            val isCharacterFavorite: Boolean,

            ) : UiModel()

        class ShowToast(val msgResource: Int) : UiModel()
        class UpdateComics(val comicList: List<Result>) : UiModel()
    }

    private fun refresh() {
        _model.value = UiModel.SetHeroDetails(::onFabClick, ::onHeroShown)
    }

    /**
     * Checks if the current character is favorite and get the character's comics
     */
    private fun onHeroShown(heroId: Int) {
        viewModelScope.launch {
            try {
                _model.value = UiModel.Loading
                val isCharacterFavorite = useCaseIsCharacterFavoriteImpl.invoke(heroId)
                _model.value = UiModel.UpdateFAB(isCharacterFavorite)
                val comic = useCaseGetComicsRemoteImpl.invoke(heroId)
                val comicList = comic.dataComics?.resultComics ?: emptyList()
                _model.value = UiModel.UpdateComics(comicList.fromListResult())
            } catch (e: UnknownHostException) {
                Timber.e("qq_MainPresenter.onCreate: $e")
                _model.value = UiModel.ShowToast(R.string.no_internet)
            }
        }
    }

    private fun onFabClick(
        selectedHero: CharacterResult,
        isCharacterFavorite: Boolean,
    ) {
        viewModelScope.launch {
            if (isCharacterFavorite) {
                useCaseInsertFavoriteCharacterImpl.invoke(selectedHero.toCharacterResultDomain())
            } else {
                useCaseDeleteFavoriteCharacterImpl.invoke(selectedHero.toCharacterResultDomain())
            }
        }
    }
}
