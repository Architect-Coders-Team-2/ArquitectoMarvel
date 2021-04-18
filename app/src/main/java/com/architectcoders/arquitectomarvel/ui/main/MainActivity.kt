package com.architectcoders.arquitectomarvel.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.ActivityMainBinding
import com.architectcoders.arquitectomarvel.model.*
import com.architectcoders.arquitectomarvel.ui.detail.HeroDetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: AdapterList
    private var viewItem: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initViewModel()
    }

    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mainHeroList.autoFitColumnsForGridLayout(resources.getDimension(R.dimen.avatar_width))
    }

    private fun initViewModel() {
        viewModel = getViewModel { MainViewModel(Repository(application)) }
        adapter = AdapterList(viewModel::onResultClick)
        binding.mainHeroList.adapter = adapter
        viewModel.model.observe(this, Observer(::updateUI))
        viewModel.navigation.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { result ->
                viewModel.viewItem.value?.let {
                    viewItem = it
                }
                viewItem?.let {
                    val options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            this,
                            viewItem!!,
                            getString(R.string.hero_image)
                        )
                    startActivity<HeroDetailActivity>(options = options.toBundle()) {
                        putExtra(EXTRA_SELECTED_HERO, result)
                    }
                }
            }
        })
    }

    private fun updateUI(model: MainViewModel.UiModel) {
        binding.progress.isVisible = (model == MainViewModel.UiModel.Loading)
        when (model) {
            is MainViewModel.UiModel.GetRemoteData -> adapter.submitList(model.results)
            is MainViewModel.UiModel.GetErrorMessage -> toast(model.message)
        }

    }

}