package com.architectcoders.arquitectomarvel.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.*
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.ActivityMainBinding
import com.architectcoders.arquitectomarvel.databinding.ReposLoadStateFooterViewItemBinding
import com.architectcoders.arquitectomarvel.model.*
import com.architectcoders.arquitectomarvel.ui.detail.HeroDetailActivity
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel> {
        Factory(Repository(application))
    }
    private val adapter: AdapterList by lazy {
        AdapterList(viewModel::onResultClick)
    }
    private var viewItem: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
        observersViewModel()
    }

    private fun setUpViews() {
        val mLayoutManager = GridLayoutManager(this@MainActivity, 3)
        val footerAdapter = ResultsLoadStateAdapter(adapter::retry)
        binding.apply {
            mainHeroList.autoFitColumnsForGridLayout(resources.getDimension(R.dimen.avatar_width))
            mainHeroList.layoutManager = mLayoutManager
            mainHeroList.adapter = adapter.withLoadStateFooter(footerAdapter)
            mLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == adapter.itemCount && footerAdapter.itemCount > 0) {
                        3
                    } else {
                        1
                    }
                }
            }
        }
        adapter.addLoadStateListener {
            binding.progress.isVisible = it.refresh is LoadState.Loading
        }
    }

    private fun observersViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.pager.collectLatest {
                adapter.submitData(lifecycle, it)
            }
        }

        viewModel.navigation.observe(this) { event ->
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

}