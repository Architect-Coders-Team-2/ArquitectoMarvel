package com.architectcoders.arquitectomarvel.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.*
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.ActivityMainBinding
import com.architectcoders.arquitectomarvel.model.*
import com.architectcoders.arquitectomarvel.model.database.ResultDatabase
import com.architectcoders.arquitectomarvel.model.database.RoomDataSource
import com.architectcoders.arquitectomarvel.ui.common.Event
import com.architectcoders.arquitectomarvel.ui.common.calculateColumsForGridLayout
import com.architectcoders.arquitectomarvel.ui.common.getViewModel
import com.architectcoders.arquitectomarvel.ui.common.startActivity
import com.architectcoders.arquitectomarvel.ui.detail.HeroDetailActivity
import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.characters.Result
import com.architectcoders.arquitectomarvel.BuildConfig
import com.architectcoders.usecase.GetCharacters
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy {
        getViewModel {
            MainViewModel(
                GetCharacters(
                    MarvelRepository(
                        MarvelDataSource(),
                        RoomDataSource(ResultDatabase.getInstance(this)),
                        BuildConfig.MARVEL_API_KEY
                    )
                )
            )
        }
    }
    private val adapter: AdapterList by lazy {
        AdapterList(::navigateTo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        observersViewModel()
    }

    private fun initViews() {
        val columns = calculateColumsForGridLayout(resources.getDimension(R.dimen.avatar_width))
        val layoutManager = GridLayoutManager(this@MainActivity, columns)
        val footerAdapter = ResultsLoadStateAdapter(adapter::retry)
        binding.apply {
            mainHeroList.layoutManager = layoutManager
            mainHeroList.adapter = adapter.withLoadStateFooter(footerAdapter)

            // This helps to centre the ProgressBar by using the number of columns from the main list
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == adapter.itemCount && footerAdapter.itemCount > 0) {
                        columns
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
    }

    private fun navigateTo(result: Result, view: View) {
            Event(result).getContentIfNotHandled()?.let { resultValue ->
                val options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this,
                        view,
                        getString(R.string.hero_image)
                    )
                startActivity<HeroDetailActivity>(options = options.toBundle()) {
                    putExtra(EXTRA_SELECTED_HERO, resultValue.id)
                }
            }
        }

}