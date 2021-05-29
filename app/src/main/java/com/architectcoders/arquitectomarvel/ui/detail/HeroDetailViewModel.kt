package com.architectcoders.arquitectomarvel.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.arquitectomarvel.BuildConfig
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.model.database.ComicEntity
import com.architectcoders.arquitectomarvel.model.database.toComicResult
import com.architectcoders.arquitectomarvel.ui.common.md5
import com.architectcoders.usecase.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException
import com.architectcoders.domain.characters.Result as HeroResult
import com.architectcoders.domain.comics.Result as ComicResult

class HeroDetailViewModel(
    private val getCharacterById: GetCharacterByIdRemote,
    private val isCharacterFavorite: IsHeroFavorite,
    private val getComicsFromCharacterId: GetComicsFromHeroIdRemote,
    private val insertFavoriteCharacter: InsertFavoriteHero,
    private val insertFavoriteComic: InsertFavoriteComic,
    private val deleteFavoriteCharacter: DeleteFavoriteHero,
    private val deleteFavoriteComic: DeleteFavoriteComic
) : ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class RequestCharacterById(val listener: (Int) -> Unit) : UiModel()
        class SetHeroDetails(val character: HeroResult?) : UiModel()
        class UpdateFAB(
            val isCharacterFavorite: Boolean,
            val listener: (
                selectedHero: HeroResult,
                comicList: MutableList<ComicEntity>,
                isCharacterFavorite: Boolean,
            ) -> Unit,
        ) : UiModel()

        class ShowToast(val msgResource: Int) : UiModel()
        class UpdateComics(val comicList: List<ComicResult>) : UiModel()
    }

    private fun refresh() {
        _model.value = UiModel.RequestCharacterById(::onHeroShown)
    }

    /**
     * Checks if the current character is favorite and get the character's comics
     */
    private fun onHeroShown(heroId: Int) {
        viewModelScope.launch {
            try {
                val ts = System.currentTimeMillis()
                _model.value = UiModel.Loading
                getCharacterId(heroId, ts)
                isCharacterFavorite(heroId)
                _model.value = UiModel.Loading
                getComicsFromCharacterId(heroId, ts)
            } catch (e: UnknownHostException) {
                Timber.e("qq_MainPresenter.onCreate: $e")
                _model.value = UiModel.ShowToast(R.string.no_internet)
            }
        }
    }


    private suspend fun getCharacterId(characterId: Int, ts: Long) {
        val characters = getCharacterById.invoke(
            characterId,
            ts,
            "$ts${BuildConfig.MARVEL_PRIVATE_KEY}${BuildConfig.MARVEL_API_KEY}".md5
        )
        _model.value = UiModel.SetHeroDetails(characters.characterData?.results?.first())
    }

    private suspend fun isCharacterFavorite(characterId: Int) {
        val isCharacterFavorite = isCharacterFavorite.invoke(characterId)
        _model.value = UiModel.UpdateFAB(isCharacterFavorite, ::onFabClick)
    }

    private suspend fun getComicsFromCharacterId(characterId: Int, ts: Long) {
        val comic = getComicsFromCharacterId.invoke(
            characterId, ts,
            "$ts${BuildConfig.MARVEL_PRIVATE_KEY}${BuildConfig.MARVEL_API_KEY}".md5
        )
        val comicList = comic?.comicData?.results ?: emptyList()
        _model.value = UiModel.UpdateComics(comicList)
    }


    private fun onFabClick(
        selectedHero: HeroResult,
        comicList: MutableList<ComicEntity>,
        isCharacterFavorite: Boolean,
    ) {
        viewModelScope.launch {
            if (isCharacterFavorite) {
                insertFavoriteCharacter.invoke(selectedHero)
                comicList.forEach {
                    insertFavoriteComic.invoke(it.toComicResult)
                }
            } else {
                deleteFavoriteCharacter.invoke(selectedHero)
                comicList.forEach {
                    deleteFavoriteComic.invoke(it.toComicResult)
                }
            }
        }
    }
}
