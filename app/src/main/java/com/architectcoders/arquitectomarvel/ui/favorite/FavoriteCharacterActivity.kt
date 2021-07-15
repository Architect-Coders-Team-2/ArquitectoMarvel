package com.architectcoders.arquitectomarvel.ui.favorite

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.ActivityFavoriteCharacterBinding
import com.architectcoders.arquitectomarvel.ui.common.*
import com.architectcoders.arquitectomarvel.ui.detail.CharacterDetailActivity
import com.architectcoders.domain.character.Character
import com.architectcoders.usecases.GetLocalFavoriteCharacters
import com.architectcoders.usecases.ManageNetworkManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
@AndroidEntryPoint
class FavoriteCharacterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteCharacterBinding
    private var menuItem: MenuItem? = null
    private val networkRepository by lazy {
        ServiceLocator.provideNetworkRepository(
            applicationContext
        )
    }

    private val favoriteCharacterViewModel: FavoriteCharacterViewModel by viewModels()

    private val favoriteCharacterAdapter: FavoriteCharacterAdapter by lazy {
        FavoriteCharacterAdapter(::navigateTo)
    }

    private fun navigateTo(character: Character, view: View) {
        Event(character).getContentIfNotHandled()?.let {resultValue  ->
            val options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    view,
                    getString(R.string.character_image)
                )
            startActivity<CharacterDetailActivity>(options = options.toBundle()) {
                putExtra(EXTRA_SELECTED_CHARACTER, resultValue.id)
            }
        }
    }

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.favorite_characters)
        initFavoriteList()
        manageNetworkManager()
        updateUi()
    }

    private fun initFavoriteList() {
        val columns = calculateColumnsForGridLayout(resources.getDimension(R.dimen.avatar_width))
        val gridLayoutManager = GridLayoutManager(this, columns)
        binding.favoriteCharacterList.apply {
            layoutManager = gridLayoutManager
            adapter = favoriteCharacterAdapter
        }
    }

    private fun manageNetworkManager() {
        lifecycleScope.launchWhenStarted {
            ManageNetworkManager(networkRepository).invoke(lifecycle, ::shouldShowOfflineIcon)
        }
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

    private fun updateUi() {
        lifecycleScope.launchWhenStarted {
            favoriteCharacterViewModel.uiModel.collect {
                updateFavoriteCharacterList(it)
            }
        }
    }

    private suspend fun updateFavoriteCharacterList(uiModel: FavoriteCharacterViewModel.UiModel) {
        binding.progress.isVisible = uiModel is FavoriteCharacterViewModel.UiModel.Loading
        if (uiModel is FavoriteCharacterViewModel.UiModel.RetrieveFavoriteCharacters) {
            uiModel.favoriteCharacterList.collect {
                binding.noFavorites.isVisible = it.isEmpty()
                favoriteCharacterAdapter.submitList(it)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
