package com.architectcoders.arquitectomarvel.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.data.database.toComicComicList
import com.architectcoders.arquitectomarvel.databinding.ActivityCharacterDetailBinding
import com.architectcoders.arquitectomarvel.ui.common.*
import com.architectcoders.arquitectomarvel.ui.detail.CharacterDetailViewModel.UiModel
import com.architectcoders.domain.character.Character
import com.architectcoders.domain.comic.Comic
import com.architectcoders.usecases.*
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
                GetRemoteComicsFromCharacterId(characterRepository),
                InsertLocalFavoriteCharacter(characterRepository),
                DeleteLocalFavoriteCharacter(characterRepository),
                GetComicsInteractor(
                    GetRemoteComicsFromCharacterId(characterRepository),
                    InsertComicsForCharacterLocal(characterRepository),
                    GetComicsForHero(characterRepository)
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
        binding.contentHeroDetail.comicList.adapter = adapter
        characterDetailViewModel.model.observe(this, Observer(::updateUi))
        manageNetworkManager()
        showComics()
    }


    private fun showComics() {
        binding.contentHeroDetail.apply {
            characterDetailViewModel.comics.observe(this@CharacterDetailActivity) { comicEntity ->
                adapter.submitList(comicEntity.data?.toComicComicList ?: emptyList())
                progress.isVisible = comicEntity is Resource.Loading && comicEntity.data.isNullOrEmpty()
                noComics.isVisible = comicEntity is Resource.Error && comicEntity.data.isNullOrEmpty()
                if (comicEntity.error is UnknownHostException) {
                    noComics.text = getString(R.string.no_chached_comics)
                }
            }
        }

    }

    private fun manageNetworkManager() {
        lifecycleScope.launchWhenStarted {
            ManageNetworkManager(networkRepository).invoke(lifecycle, ::shouldShowOfflineMessage)
        }
    }

    private fun shouldShowOfflineMessage(internetAvailable: Boolean) {
        binding.contentHeroDetail.offlineStatus.isVisible = !internetAvailable
    }

    private fun updateUi(model: UiModel) {
        binding.contentHeroDetail.progress.isVisible = model is UiModel.Loading
        when (model) {
            is UiModel.SetCharacterDetails -> setCharacterDetails(model.character)
            is UiModel.UpdateFAB -> updateFAB(model.isCharacterFavorite, model.listener)
            is UiModel.UpdateComics -> updateComics(model.comicList)
        }
    }

    private fun setCharacterDetails(character: Character) {
        this.selectedCharacter = character
        binding.headerHeroImage.loadUrl(
            character.thumbnail?.path,
            character.thumbnail?.extension
        )
        binding.toolbar.title = character.name ?: EMPTY_TEXT
        binding.toolbarLayout.title = character.name ?: EMPTY_TEXT
        binding.contentHeroDetail.heroContent.text =
            if (character.description.isNullOrBlank()) {
                getString(R.string.content_not_available)
            } else {
                character.description
            }
    }

    private fun updateFAB(
        isCharacterFavorite: Boolean,
        listener: (
            selectedHero: Character,
            comicList: MutableList<Comic>,
            isCharacterFavorite: Boolean,
        ) -> Unit
    ) {
        setCharacterFavorite(isCharacterFavorite)
        listenToFab(listener)
    }

    private fun listenToFab(
        listener: (
            selectedHero: Character,
            comicList: MutableList<Comic>,
            isCharacterFavorite: Boolean,
        ) -> Unit
    ) {
        binding.fab.setOnClickListener {
            selectedCharacter?.let { character ->
                setCharacterFavorite(this.isCharacterFavorite.not())
                listener(character, adapter.currentList, this.isCharacterFavorite)
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

    private fun updateComics(comicList: List<Comic>) {
        if (comicList.isEmpty()) {
            binding.contentHeroDetail.noComics.isVisible = true
        }
        adapter.submitList(comicList)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
