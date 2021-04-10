package com.architectcoders.arquitectomarvel.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.ActivityMainBinding
import com.architectcoders.arquitectomarvel.model.*
import com.architectcoders.arquitectomarvel.ui.main.MainViewModel.UiModel.*;

import timber.log.Timber

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

    }

    fun updateUI(model: MainViewModel.UiModel) {
        binding.progress.isVisible = (model == Loading)
        when (model) {
            is GetRemoteData -> adapter.submitList(model.results)
            //TODO: Implement Navigation to DetailActivity
            is Navigation -> Timber.d("qq_MainActivity.navigateTo: ${model.result.comics.collectionURI}")
            is GetErrorMessage -> toast(model.message)
          //  is UpdateLocalData -> adapter.submitList(model.results)

        }

    }
}