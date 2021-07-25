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
import com.architectcoders.arquitectomarvel.ui.common.NetworkLogicViewModel.UiNetworkModel
import com.architectcoders.arquitectomarvel.ui.detail.CharacterDetailActivity
import com.architectcoders.arquitectomarvel.ui.favorite.FavoriteCharacterViewModel.UiModel
import com.architectcoders.domain.character.Character
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteCharacterActivity : AppCompatActivity() {

    @Inject
    lateinit var favoriteCharacterAdapter: FavoriteCharacterAdapter

    private lateinit var binding: ActivityFavoriteCharacterBinding
    private var menuItem: MenuItem? = null
    private val favoriteCharacterViewModel: FavoriteCharacterViewModel by viewModels()
    private val networkLogicViewModel: NetworkLogicViewModel by viewModels()

    private fun navigateTo(character: Character, view: View) {
        Event(character).getContentIfNotHandled()?.let { resultValue ->
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
        updateUi()
    }

    private fun initFavoriteList() {
        val columns = calculateColumnsForGridLayout(resources.getDimension(R.dimen.avatar_width))
        val gridLayoutManager = GridLayoutManager(this, columns)
        favoriteCharacterAdapter.listener = ::navigateTo
        binding.favoriteCharacterList.apply {
            layoutManager = gridLayoutManager
            adapter = favoriteCharacterAdapter
        }
    }

    private fun updateUi() {
        lifecycleScope.launchWhenStarted {
            networkLogicViewModel.uiNetworkModel.collect {
                updateNetworkUi(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            favoriteCharacterViewModel.uiModel.collect {
                updateFavoriteCharacterList(it)
            }
        }
    }

    private fun updateNetworkUi(uiNetworkModel: UiNetworkModel) {
        when (uiNetworkModel) {
            is UiNetworkModel.InitNetworkManager -> uiNetworkModel.listener(lifecycle)
            is UiNetworkModel.SetNetworkAvailability -> shouldShowOfflineIcon(uiNetworkModel.isAvailable)
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

    private suspend fun updateFavoriteCharacterList(uiModel: UiModel) {
        binding.progress.isVisible = uiModel is UiModel.Loading
        if (uiModel is UiModel.RetrieveFavoriteCharacters) {
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
