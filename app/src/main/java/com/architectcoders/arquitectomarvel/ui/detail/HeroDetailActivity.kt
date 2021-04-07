package com.architectcoders.arquitectomarvel.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.ActivityHeroDetailBinding
import com.architectcoders.arquitectomarvel.model.*
import com.architectcoders.arquitectomarvel.model.characters.Result

class HeroDetailActivity : AppCompatActivity(), HeroDetailPresenter.View {

    lateinit var binding: ActivityHeroDetailBinding
    private val presenter: HeroDetailPresenter by lazy { HeroDetailPresenter(Repository(this)) }
    val adapter by lazy { ComicAdapter() }
    var selectedHero: Result? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroDetailBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.contentHeroDetail.comicList.adapter = adapter
        binding.fab.setOnClickListener {
            presenter.onFabClick()
        }
        presenter.onCreate(this)
    }

    override fun setHeroDetails() {
        intent.extras?.getParcelable<Result>(EXTRA_SELECTED_HERO)?.let { selectedHero ->
            this.selectedHero = selectedHero
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

    override fun showProgress() {
        binding.contentHeroDetail.progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.contentHeroDetail.progress.visibility = View.GONE
    }

    override fun showToast(msgResource: Int) {
        toast(msgResource)
    }

    override fun updateComics(comicList: List<com.architectcoders.arquitectomarvel.model.comics.Result>) {
        if (comicList.isEmpty())
        {
            binding.contentHeroDetail.noComics.visibility = View.VISIBLE
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

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}
