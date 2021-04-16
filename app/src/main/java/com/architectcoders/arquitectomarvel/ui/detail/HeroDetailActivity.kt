package com.architectcoders.arquitectomarvel.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.ActivityHeroDetailBinding
import com.architectcoders.arquitectomarvel.model.*
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.model.database.toDetailedComicEntityList

class HeroDetailActivity : AppCompatActivity(), HeroDetailPresenter.View {

    lateinit var binding: ActivityHeroDetailBinding
    private val presenter: HeroDetailPresenter by lazy { HeroDetailPresenter(Repository(application)) }
    val adapter by lazy { ComicAdapter() }
    var selectedCharacter: Result? = null
    var isCharacterFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroDetailBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter.onCreate(this)
    }

    override fun initComicListAdapter() {
        binding.contentHeroDetail.comicList.adapter = adapter
    }

    override fun setHeroDetails() {
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
            presenter.onHeroShown(selectedHero.id)
        }
    }

    /**
     * Initially saves if the character is favorite, updates the FAB image and sets the click listener
     * if the character is available
     */
    override fun updateFAB(isCharacterFavorite: Boolean) {
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
                presenter.onFabClick(character, adapter.currentList, this.isCharacterFavorite)
            }
        }
    }

    override fun showProgress() {
        binding.contentHeroDetail.progress.isVisible = true
    }

    override fun hideProgress() {
        binding.contentHeroDetail.progress.isVisible = false
    }

    override fun showToast(msgResource: Int) {
        toast(msgResource)
    }

    override fun updateComics(comicList: List<com.architectcoders.arquitectomarvel.model.comics.Result>) {
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

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}
