package com.architectcoders.arquitectomarvel.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    private fun initViewModel(){
        viewModel = getViewModel { MainViewModel(Repository(application)) }
        adapter = AdapterList(viewModel::onResultClick)
        binding.mainHeroList.adapter = adapter
        viewModel.model.observe(this, Observer(::updateUI))
        viewModel.navigation.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {

                toast("Hero selected ${it.name}")
                //TODO: putExtra Result: it
                //startActivity<HeroDetailActivity> {}
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