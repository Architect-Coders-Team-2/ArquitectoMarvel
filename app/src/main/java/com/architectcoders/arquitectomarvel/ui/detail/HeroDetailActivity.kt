package com.architectcoders.arquitectomarvel.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.architectcoders.arquitectomarvel.BuildConfig
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.model.database.ComicEntity
import com.architectcoders.arquitectomarvel.databinding.ActivityHeroDetailBinding
import com.architectcoders.arquitectomarvel.model.EMPTY_TEXT
import com.architectcoders.arquitectomarvel.model.EXTRA_SELECTED_HERO
import com.architectcoders.arquitectomarvel.model.MarvelDataSource
import com.architectcoders.arquitectomarvel.model.database.ResultDatabase
import com.architectcoders.arquitectomarvel.model.database.RoomDataSource
import com.architectcoders.arquitectomarvel.model.database.toComicEntityList
import com.architectcoders.arquitectomarvel.ui.common.*
import com.architectcoders.arquitectomarvel.ui.detail.HeroDetailViewModel.UiModel
import com.architectcoders.arquitectomarvel.ui.common.getViewModel
import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecase.*
import com.architectcoders.domain.characters.Result as HeroResult

class HeroDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityHeroDetailBinding
    private val adapter by lazy { ComicAdapter() }
    private var selectedCharacter: HeroResult? = null
    private var isCharacterFavorite = false
    private val heroDetailViewModel by lazy {
        getViewModel {
            val marvelRepository = MarvelRepository(
                MarvelDataSource(),
                RoomDataSource(ResultDatabase.getInstance(this)),
                BuildConfig.MARVEL_API_KEY
            )
            HeroDetailViewModel(
                GetCharacterByIdRemote(marvelRepository),
                IsHeroFavorite(marvelRepository),
                GetComicsFromHeroIdRemote(marvelRepository),
                InsertFavoriteHero(marvelRepository),
                InsertFavoriteComic(marvelRepository),
                DeleteFavoriteHero(marvelRepository),
                DeleteFavoriteComic(marvelRepository)
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroDetailBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.contentHeroDetail.comicList.adapter = adapter
        heroDetailViewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: UiModel) {
        binding.contentHeroDetail.progress.isVisible = model is UiModel.Loading
        when (model) {
            is UiModel.RequestCharacterById -> model.listener(
                intent.extras?.getInt(
                    EXTRA_SELECTED_HERO
                ) ?: 0
            )
            is UiModel.SetHeroDetails -> setHeroDetails(model.character)
            is UiModel.ShowToast -> toast(model.msgResource)
            is UiModel.UpdateFAB -> updateFAB(model.isCharacterFavorite, model.listener)
            is UiModel.UpdateComics -> updateComics(model.comicList.toComicEntityList)
        }
    }

    private fun setHeroDetails(character: HeroResult?) {
        character?.let { selectedHero ->
            this.selectedCharacter = selectedHero
            binding.headerHeroImage.loadUrl(
                selectedHero.thumbnail?.path,
                selectedHero.thumbnail?.extension
            )
            binding.toolbar.title = selectedHero.name ?: EMPTY_TEXT
            binding.toolbarLayout.title = selectedHero.name ?: EMPTY_TEXT
            binding.contentHeroDetail.heroContent.text =
                if (selectedHero.description.isNullOrBlank()) {
                    getString(R.string.content_not_available)
                } else {
                    selectedHero.description
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
            selectedHero: HeroResult,
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
