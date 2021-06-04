package com.architectcoders.arquitectomarvel.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.*
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.data.*
import com.architectcoders.arquitectomarvel.databinding.ActivityMainBinding
import com.architectcoders.arquitectomarvel.ui.common.*
import com.architectcoders.arquitectomarvel.ui.detail.CharacterDetailActivity
import com.architectcoders.arquitectomarvel.ui.main.pagination.CharacterAdapter
import com.architectcoders.arquitectomarvel.ui.main.pagination.LoadStateAdapter
import com.architectcoders.domain.characters.Hero
import com.architectcoders.usecases.*
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by lazy {
        val marvelRepository = ServiceLocator.provideMarvelRepository(this)
        getViewModel {
            MainViewModel(
                GetRemoteCharacters(marvelRepository),
                DeleteAllLocalCharacters(marvelRepository),
                InsertAllLocalCharacters(marvelRepository),
                GetLastTimeStampFromCharacterEntity(marvelRepository),
                GetPagingSourceFromCharacterEntity(marvelRepository),
                GetLocalCharactersCount(marvelRepository)
            )
        }
    }

    private val characterAdapter: CharacterAdapter by lazy {
        CharacterAdapter(::navigateTo)
    }

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()
        collectLatestPager()
    }

    private fun setUpViews() {
        val columns = calculateColumnsForGridLayout(resources.getDimension(R.dimen.avatar_width))
        val layoutManager = GridLayoutManager(this@MainActivity, columns)
        val footerAdapter = LoadStateAdapter(characterAdapter::retry)
        binding.apply {
            mainHeroList.layoutManager = layoutManager
            mainHeroList.adapter = characterAdapter.withLoadStateFooter(footerAdapter)
        }

        // This helps to centre the ProgressBar by using the number of columns from the main list
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == characterAdapter.itemCount && footerAdapter.itemCount > 0) {
                    columns
                } else {
                    1
                }
            }
        }
        characterAdapter.addLoadStateListener {
            binding.progress.isVisible = it.refresh is LoadState.Loading
        }
    }

    @ExperimentalPagingApi
    private fun collectLatestPager() {
        lifecycleScope.launchWhenStarted {
            viewModel.pager.collectLatest {
                characterAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun navigateTo(hero: Hero, view: View) {
        Event(hero).getContentIfNotHandled()?.let { resultValue ->
            val options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    view,
                    getString(R.string.hero_image)
                )
            startActivity<CharacterDetailActivity>(options = options.toBundle()) {
                putExtra(EXTRA_SELECTED_HERO, resultValue.id)
            }
        }
    }
}
