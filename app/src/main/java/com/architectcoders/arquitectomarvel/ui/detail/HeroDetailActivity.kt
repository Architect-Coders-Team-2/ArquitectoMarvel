package com.architectcoders.arquitectomarvel.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.ActivityHeroDetailBinding
import com.architectcoders.arquitectomarvel.model.*
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.model.database.DetailedComicEntity
import com.architectcoders.arquitectomarvel.model.database.toDetailedComicEntityList
import com.architectcoders.arquitectomarvel.ui.detail.HeroDetailViewModel.UiModel
import com.architectcoders.arquitectomarvel.model.comics.Result as ComicResult

class HeroDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityHeroDetailBinding
    private lateinit var heroDetailViewModel: HeroDetailViewModel
    private val adapter by lazy { ComicAdapter() }
    var selectedCharacter: Result? = null
    var isCharacterFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroDetailBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.contentHeroDetail.comicList.adapter = adapter
        heroDetailViewModel = getViewModel { HeroDetailViewModel(Repository(application)) }
        heroDetailViewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: UiModel) {
        binding.contentHeroDetail.progress.isVisible = model is UiModel.Loading
        when (model) {
            is UiModel.SetHeroDetails -> setHeroDetails(model.onHeroShown)
            is UiModel.ShowToast -> toast(model.msgResource)
            is UiModel.UpdateFAB -> updateFAB(model.isCharacterFavorite, model.listener)
            is UiModel.UpdateComics -> updateComics(model.comicList)
        }
    }

    private fun setHeroDetails(onHeroShown: (Int) -> Unit) {
        intent.extras?.getParcelable<Result>(EXTRA_SELECTED_HERO)?.let { selectedHero ->
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
            onHeroShown(selectedHero.id)
        }
    }

    /**
     * Initially saves if the character is favorite, updates the FAB image and sets the click listener
     * if the character is available
     */
    private fun updateFAB(
        isCharacterFavorite: Boolean,
        listener: (
            selectedHero: Result,
            comicList: MutableList<DetailedComicEntity>,
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

    private fun updateComics(comicList: List<ComicResult>) {
        if (comicList.isEmpty()) {
            binding.contentHeroDetail.noComics.isVisible = true
        }
        adapter.submitList(comicList.toDetailedComicEntityList)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
