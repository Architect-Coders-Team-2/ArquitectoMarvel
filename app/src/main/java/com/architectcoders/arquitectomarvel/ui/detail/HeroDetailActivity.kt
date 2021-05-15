package com.architectcoders.arquitectomarvel.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.data.local.entities.toDetailedComicEntityList
import com.architectcoders.arquitectomarvel.data.ui_models.ResultUI
import com.architectcoders.arquitectomarvel.data.ui_models.fromResultUItoCharacterResult
import com.architectcoders.arquitectomarvel.databinding.ActivityHeroDetailBinding
import com.architectcoders.arquitectomarvel.ui.common.EXTRA_SELECTED_HERO
import com.architectcoders.arquitectomarvel.ui.common.loadImage
import com.architectcoders.arquitectomarvel.ui.common.loadUrl
import com.architectcoders.arquitectomarvel.ui.common.toast
import com.architectcoders.arquitectomarvel.ui.detail.HeroDetailViewModel.UiModel
import dagger.hilt.android.AndroidEntryPoint
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.Result as CharacterResult
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.comics.Result as ComicsResult

@AndroidEntryPoint
class HeroDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHeroDetailBinding

    private val heroDetailViewModel: HeroDetailViewModel by viewModels()

    private val adapter by lazy { ComicAdapter() }
    private var isCharacterFavorite = false

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
            is UiModel.SetHeroDetails -> setHeroDetails(model.listener, model.onHeroShown)
            is UiModel.ShowToast -> toast(model.msgResource)
            is UiModel.UpdateFAB -> updateFAB(model.isCharacterFavorite)
            is UiModel.UpdateComics -> updateComics(model.comicList)
        }
    }

    private fun setHeroDetails(
        listener: (
            selectedHero: CharacterResult,
            isCharacterFavorite: Boolean,
        ) -> Unit,
        onHeroShown: (Int) -> Unit
    ) {
        intent.extras?.getParcelable<ResultUI>(EXTRA_SELECTED_HERO)?.let { selectedHero ->
            binding.headerHeroImage.loadUrl(
                selectedHero.thumbnail.path,
                selectedHero.thumbnail.extension
            )
            binding.toolbar.title = selectedHero.name
            binding.toolbarLayout.title = selectedHero.name
            binding.contentHeroDetail.heroContent.text =
                if (selectedHero.description.isBlank()) {
                    getString(R.string.content_not_available)
                } else {
                    selectedHero.description
                }
            binding.fab.setOnClickListener {
                this.isCharacterFavorite = this.isCharacterFavorite.not()
                binding.fab.loadImage(
                    android.R.drawable.star_on,
                    android.R.drawable.star_off,
                    this.isCharacterFavorite
                )
                listener(
                    selectedHero.fromResultUItoCharacterResult(),
                    this.isCharacterFavorite
                )

            }
            onHeroShown(selectedHero.id)
        }
    }

    /**
     * Initially saves if the character is favorite, updates the FAB image and sets the click listener
     * if the character is available
     */
    private fun updateFAB(
        isCharacterFavorite: Boolean
    ) {
        this.isCharacterFavorite = isCharacterFavorite
        binding.fab.loadImage(
            android.R.drawable.star_on,
            android.R.drawable.star_off,
            this.isCharacterFavorite
        )
    }

    private fun updateComics(comicList: List<ComicsResult>) {
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
