package com.architectcoders.arquitectomarvel.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.ActivityCharacterDetailBinding
import com.architectcoders.arquitectomarvel.ui.common.*
import com.architectcoders.arquitectomarvel.ui.detail.CharacterDetailViewModel.UiModel
import com.architectcoders.usecases.*
import com.architectcoders.domain.characters.Result as CharacterResult
import com.architectcoders.domain.comics.Result as ComicResult

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding
    private val adapter by lazy { ComicAdapter() }
    private var selectedCharacter: CharacterResult? = null
    private var isCharacterFavorite = false
    private val characterDetailViewModel by lazy {
        getViewModel {
            val characterRepository = ServiceLocator.provideMarvelRepository(this)
            CharacterDetailViewModel(
                intent.getIntExtra(EXTRA_SELECTED_HERO, 0),
                GetCharacterById(characterRepository),
                IsCharacterFavorite(characterRepository),
                GetComicsFromCharacterId(characterRepository),
                InsertFavoriteCharacter(characterRepository),
                InsertFavoriteComic(characterRepository),
                DeleteFavoriteCharacter(characterRepository),
                DeleteFavoriteComic(characterRepository)
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
    }

    private fun updateUi(model: UiModel) {
        binding.contentHeroDetail.progress.isVisible = model is UiModel.Loading
        when (model) {
            is UiModel.SetCharacterDetails -> setCharacterDetails(model.character)
            is UiModel.ShowToast -> toast(model.msgResource)
            is UiModel.UpdateFAB -> updateFAB(model.isCharacterFavorite, model.listener)
            is UiModel.UpdateComics -> updateComics(model.comicList)
        }
    }

    private fun setCharacterDetails(character: CharacterResult) {
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
            selectedHero: CharacterResult,
            comicList: MutableList<ComicResult>,
            isCharacterFavorite: Boolean,
        ) -> Unit
    ) {
        setCharacterFavorite(isCharacterFavorite)
        listenToFab(listener)
    }

    private fun listenToFab(
        listener: (
            selectedHero: CharacterResult,
            comicList: MutableList<ComicResult>,
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

    private fun updateComics(comicList: List<ComicResult>) {
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
