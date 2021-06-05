package com.architectcoders.arquitectomarvel.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
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
import com.architectcoders.domain.characters.Result
import com.architectcoders.usecases.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var menuItem: MenuItem? = null
    private val networkRepository by lazy {
        ServiceLocator.provideNetworkRepository(
            applicationContext
        )
    }
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

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            RegisterNetworkManager(networkRepository).invoke(::shouldShowOfflineIcon)
        }
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

    private fun navigateTo(result: Result, view: View) {
        Event(result).getContentIfNotHandled()?.let { resultValue ->
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

    /**
     * Sets the offline icon if needed
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let { menuValue ->
            menuItem = menuValue.add(1, 1, 1, getString(R.string.offline))
            val drawable = ContextCompat.getDrawable(this, R.drawable.ic_cloud_off_black_24dp)
            drawable?.let {
                DrawableCompat.setTint(it, ContextCompat.getColor(this, R.color.white))
                menuItem?.setIcon(drawable)
            }
            menuItem?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        }
        return true
    }

    /**
     * If the visibility suddenly changes when we had already changed it a few seconds ago,
     * we need to wait to avoid race conditions.
     */
    private fun shouldShowOfflineIcon(internetAvailable: Boolean) {
        lifecycleScope.launchWhenStarted {
            delay(200)
            menuItem?.isVisible = !internetAvailable
        }
    }

    override fun onStop() {
        super.onStop()
        UnregisterNetworkManager(networkRepository).invoke()
    }
}
