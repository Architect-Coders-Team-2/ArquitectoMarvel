package com.architectcoders.arquitectomarvel.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.architectcoders.arquitectomarvel.BuildConfig
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.data.database.CharacterDatabase
import com.architectcoders.arquitectomarvel.data.database.ComicEntity
import com.architectcoders.arquitectomarvel.data.database.RoomDataSource
import com.architectcoders.arquitectomarvel.data.database.toComicEntityList
import com.architectcoders.arquitectomarvel.data.server.MarvelDataSource
import com.architectcoders.arquitectomarvel.databinding.ActivityCharacterDetailBinding
import com.architectcoders.arquitectomarvel.ui.common.*
import com.architectcoders.arquitectomarvel.ui.detail.CharacterDetailViewModel.UiModel
import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.usecases.*
import com.architectcoders.domain.characters.Result as CharacterResult

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding
    private val adapter by lazy { ComicAdapter() }
    private var selectedCharacter: CharacterResult? = null
    private var isCharacterFavorite = false
    private val characterDetailViewModel by lazy {
        getViewModel {
            val characterRepository = CharacterRepository(
                MarvelDataSource(),
                RoomDataSource(CharacterDatabase.getInstance(this)),
                BuildConfig.MARVEL_API_KEY
            )
            CharacterDetailViewModel(
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
            is UiModel.RequestCharacterById -> model.listener(
                intent.extras?.getInt(
                    EXTRA_SELECTED_HERO
                ) ?: 0
            )
            is UiModel.SetCharacterDetails -> setCharacterDetails(model.character)
            is UiModel.ShowToast -> toast(model.msgResource)
            is UiModel.UpdateFAB -> updateFAB(model.isCharacterFavorite, model.listener)
            is UiModel.UpdateComics -> updateComics(model.comicList.toComicEntityList)
        }
    }

    private fun setCharacterDetails(character: CharacterResult?) {
        character?.let { selectedCharacter ->
            this.selectedCharacter = selectedCharacter
            binding.headerHeroImage.loadUrl(
                selectedCharacter.thumbnail?.path,
                selectedCharacter.thumbnail?.extension
            )
            binding.toolbar.title = selectedCharacter.name ?: EMPTY_TEXT
            binding.toolbarLayout.title = selectedCharacter.name ?: EMPTY_TEXT
            binding.contentHeroDetail.heroContent.text =
                if (selectedCharacter.description.isNullOrBlank()) {
                    getString(R.string.content_not_available)
                } else {
                    selectedCharacter.description
                }
        }
    }

    /**
     * Initially saves if the character is favorite, updates the FAB image and sets the click listener
     * if the character is available
     */
    private fun updateFAB(
        isCharacterFavorite: Boolean,
        listener: (
            selectedHero: CharacterResult,
            comicList: MutableList<ComicEntity>,
            isCharacterFavorite: Boolean,
        ) -> Unit,
    ) {
        this.isCharacterFavorite = isCharacterFavorite
        binding.fab.loadImage(
            android.R.drawable.star_on,
            android.R.drawable.star_off,
            isCharacterFavorite
        )
        binding.fab.setOnClickListener {
            selectedCharacter?.let { character ->
                this.isCharacterFavorite = this.isCharacterFavorite.not()
                binding.fab.loadImage(
                    android.R.drawable.star_on,
                    android.R.drawable.star_off,
                    this.isCharacterFavorite
                )
                listener(character, adapter.currentList, this.isCharacterFavorite)
            }
        }
    }

    private fun updateComics(comicList: List<ComicEntity>) {
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
