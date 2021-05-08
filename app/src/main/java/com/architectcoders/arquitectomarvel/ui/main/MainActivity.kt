package com.architectcoders.arquitectomarvel.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.*
import com.architectcoders.arquitectomarvel.BuildConfig
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.data.*
import com.architectcoders.arquitectomarvel.data.database.CharacterDatabase
import com.architectcoders.arquitectomarvel.data.database.RoomDataSource
import com.architectcoders.arquitectomarvel.data.server.MarvelDataSource
import com.architectcoders.arquitectomarvel.databinding.ActivityMainBinding
import com.architectcoders.arquitectomarvel.ui.ViewModelFactory
import com.architectcoders.arquitectomarvel.ui.common.*
import com.architectcoders.arquitectomarvel.ui.detail.CharacterDetailActivity
import com.architectcoders.arquitectomarvel.ui.main.pagination.ResultLoadStateAdapter
import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.domain.characters.Result
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory(
            CharacterRepository(
                MarvelDataSource(),
                RoomDataSource(CharacterDatabase.getInstance(this)),
                System.currentTimeMillis(),
                BuildConfig.MARVEL_API_KEY,
                "${System.currentTimeMillis()}${BuildConfig.MARVEL_PRIVATE_KEY}${BuildConfig.MARVEL_API_KEY}".md5,
                ::log
            )
        )
    }

    private val characterAdapter: CharacterAdapter by lazy {
        CharacterAdapter(::navigateTo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()
        observersViewModel()
    }

    private fun setUpViews() {
        val columns = calculateColumnsForGridLayout(resources.getDimension(R.dimen.avatar_width))
        val layoutManager = GridLayoutManager(this@MainActivity, columns)
        val footerAdapter = ResultLoadStateAdapter(characterAdapter::retry)
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

    private fun observersViewModel() {
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

    private fun log(message: String) {
        Timber.d(message)
    }
}
