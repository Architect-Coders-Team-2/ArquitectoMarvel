package com.architectcoders.arquitectomarvel.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.ActivityMainBinding
import com.architectcoders.arquitectomarvel.model.EXTRA_SELECTED_HERO
import com.architectcoders.arquitectomarvel.model.Repository
import com.architectcoders.arquitectomarvel.model.autoFitColumnsForGridLayout
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.model.toast
import com.architectcoders.arquitectomarvel.ui.detail.HeroDetailActivity
import timber.log.Timber

class MainActivity : AppCompatActivity(), MainPresenter.View {

    private lateinit var binding: ActivityMainBinding
    private val presenter by lazy { MainPresenter(Repository(this)) }
    private val adapterList by lazy { AdapterList(presenter::onResultClick) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.onCreate(this)
    }

    override fun initViews() {
        binding.mainHeroList.autoFitColumnsForGridLayout(resources.getDimension(R.dimen.avatar_width))
        binding.mainHeroList.adapter = adapterList
    }

    override fun showProgress() {
        binding.progress.isVisible = true
    }

    override fun hideProgress() {
        binding.progress.isVisible = false
    }

    override fun updateData(list: List<Result>) {
        adapterList.submitList(list)
    }

    override fun showToastResource(msgResource: Int) {
        toast(msgResource)
    }

    override fun showToastString(msgString: String) {
        toast(msgString)
    }

    override fun navigateTo(result: Result, view: View) {
        Timber.d("qq_MainActivity.navigateTo: ${result.comics.collectionURI}")
        Intent(this, HeroDetailActivity::class.java).apply {
            putExtra(EXTRA_SELECTED_HERO, result)
        }.also {
            val options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    view,
                    getString(R.string.hero_image)
                )
            startActivity(it, options.toBundle())
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}
