package com.architectcoders.arquitectomarvel.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.ActivityMainBinding
import com.architectcoders.arquitectomarvel.model.Repository
import com.architectcoders.arquitectomarvel.model.autoFitColumnsForGridLayout
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.model.getViewModel
import com.architectcoders.arquitectomarvel.model.toast
import com.architectcoders.arquitectomarvel.ui.main.AdapterList
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
        binding.progress.isVisible = (model == MainViewModel.UiModel.Loading)
        when (model) {
            is MainViewModel.UiModel.GetRemoteData -> adapter.submitList(model.results)
            //TODO: Implement Navigation to DetailActivity
            is MainViewModel.UiModel.Navigation -> Timber.d("qq_MainActivity.navigateTo: ${model.result.comics.collectionURI}")
            is MainViewModel.UiModel.GetErrorMessage -> toast(model.message)
            //  is UpdateLocalData -> adapter.submitList(model.results)

        }

    }
}