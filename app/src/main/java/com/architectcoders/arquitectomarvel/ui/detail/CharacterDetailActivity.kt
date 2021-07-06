package com.architectcoders.arquitectomarvel.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.data.database.toComicComicList
import com.architectcoders.arquitectomarvel.databinding.ActivityCharacterDetailBinding
import com.architectcoders.arquitectomarvel.ui.common.*
import com.architectcoders.arquitectomarvel.ui.detail.CharacterDetailViewModel.UiModel
import com.architectcoders.domain.character.Character
import com.architectcoders.usecases.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.shareIn
import java.net.UnknownHostException

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding
    private val adapter by lazy { ComicAdapter() }
    private var selectedCharacter: Character? = null
    private var isCharacterFavorite = false
    private val networkRepository by lazy {
        ServiceLocator.provideNetworkRepository(
            applicationContext
        )
    }
    private val characterDetailViewModel by lazy {
        getViewModel {
            val characterRepository = ServiceLocator.provideMarvelRepository(this)
            CharacterDetailViewModel(
                intent.getIntExtra(EXTRA_SELECTED_CHARACTER, 0),
                GetLocalCharacterById(characterRepository),
                IsLocalCharacterFavorite(characterRepository),
                InsertLocalFavoriteCharacter(characterRepository),
                DeleteLocalFavoriteCharacter(characterRepository),
                GetComicsInteractor(
                    GetRemoteComicsFromCharacterId(characterRepository),
                    InsertComicsForCharacterLocal(characterRepository),
                    GetComicsForCharacter(characterRepository)
                )
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.contentCharacterDetail.comicList.adapter = adapter
        updateUi()
        manageNetworkManager()
    }

    private fun updateUi() {
        lifecycleScope.launchWhenStarted {
            characterDetailViewModel.uiModel.collect {
                updateCharacter(it)
            }
        }
        showComics()
    }

    private fun updateCharacter(uiModel: UiModel) {
        binding.contentCharacterDetail.progress.isVisible =
            uiModel is UiModel.Loading
        if (uiModel is UiModel.SetUiDetails) {
            setCharacterDetails(uiModel.character)
            updateFAB(
                uiModel.isCharacterFavorite,
                uiModel.listener
            )
        }
    }

    private fun showComics() {
        binding.contentCharacterDetail.apply {
            characterDetailViewModel.comicResurce.observe(this@CharacterDetailActivity) { comicEntity ->
                adapter.submitList(comicEntity.data?.toComicComicList ?: emptyList())
                progress.isVisible =
                    comicEntity is Resource.Loading && comicEntity.data.isNullOrEmpty()
                noComics.isVisible =
                    comicEntity is Resource.Error && comicEntity.data.isNullOrEmpty()
                if (comicEntity.error is UnknownHostException) {
                    noComics.text = getString(R.string.no_cached_comics)
                }
            }
        }
    }

    private fun setCharacterDetails(character: Character) {
        this.selectedCharacter = character
        binding.headerCharacterImage.loadUrl(
            character.thumbnail?.path,
            character.thumbnail?.extension
        )
        binding.toolbar.title = character.name ?: EMPTY_TEXT
        binding.toolbarLayout.title = character.name ?: EMPTY_TEXT
        binding.contentCharacterDetail.characterContent.text =
            if (character.description.isNullOrBlank()) {
                getString(R.string.content_not_available)
            } else {
                character.description
            }
    }

    private fun updateFAB(
        isCharacterFavorite: Flow<Int>,
        listener: (
            selectedCharacter: Character,
            isCharacterFavorite: Boolean
        ) -> Unit
    ) {
        lifecycleScope.launchWhenStarted {
            isCharacterFavorite.shareIn(lifecycleScope, SharingStarted.WhileSubscribed(), 1)
                .collect {
                    setCharacterFavorite(it > 0)
                }
        }
        listenToFab(listener)
    }

    private fun listenToFab(
        listener: (
            selectedCharacter: Character,
            isCharacterFavorite: Boolean
        ) -> Unit
    ) {
        binding.fab.setOnClickListener {
            selectedCharacter?.let { character ->
                setCharacterFavorite(isCharacterFavorite.not())
                listener(character, isCharacterFavorite)
            }
        }
    }

    private fun setCharacterFavorite(isCharacterFavorite: Boolean) {
        this.isCharacterFavorite = isCharacterFavorite
        binding.fab.loadImage(
            android.R.drawable.star_on,
            android.R.drawable.star_off,
            this.isCharacterFavorite
        )
    }

    private fun manageNetworkManager() {
        lifecycleScope.launchWhenStarted {
            ManageNetworkManager(networkRepository).invoke(lifecycle, ::shouldShowOfflineMessage)
        }
    }

    private fun shouldShowOfflineMessage(internetAvailable: Boolean) {
        binding.contentCharacterDetail.offlineStatus.isVisible = !internetAvailable
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (!isCharacterFavorite) {
            overridePendingTransition(0, 0)
        }
    }
}
