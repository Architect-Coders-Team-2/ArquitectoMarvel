package com.architectcoders.arquitectomarvel.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.ActivityMainBinding
import com.architectcoders.arquitectomarvel.model.*
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.ui.common.Event
import com.architectcoders.arquitectomarvel.ui.detail.HeroDetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: AdapterList
    private var viewItem: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        binding.mainHeroList.autoFitColumnsForGridLayout(resources.getDimension(R.dimen.avatar_width))
        viewModel = getViewModel { MainViewModel(Repository(application)) }
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        adapter = AdapterList(viewModel::onResultClick)
        binding.mainHeroList.adapter = adapter
        viewModel.navigation.observe(this, ::navigateTo)
        viewModel.errorMessage.observe(this) {
            toast(it)
        }
    }

    private fun navigateTo(event: Event<Result>) {
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
    }
}
