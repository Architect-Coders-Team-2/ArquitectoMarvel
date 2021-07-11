package com.architectcoders.arquitectomarvel.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.*
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
import com.architectcoders.arquitectomarvel.ui.favorite.FavoriteCharacterActivity
import com.architectcoders.arquitectomarvel.ui.main.pagination.CharacterAdapter
import com.architectcoders.arquitectomarvel.ui.main.pagination.LoadStateAdapter
import com.architectcoders.data.repository.BiometricRepository
import com.architectcoders.data.repository.NetworkRepository
import com.architectcoders.domain.character.Character
import com.architectcoders.usecases.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var menuItem: MenuItem? = null

    @Inject
    lateinit var networkRepository: NetworkRepository

    @Inject
    lateinit var biometricRepository: BiometricRepository

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var characterAdapter: CharacterAdapter

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        characterAdapter.listener = ::navigateTo
        setUpViews()
        collectLatestPager()
        manageNetworkManager()
        setBiometricLogic()
    }

    private fun setUpViews() {
        val columns = calculateColumnsForGridLayout(resources.getDimension(R.dimen.avatar_width))
        val layoutManager = GridLayoutManager(this@MainActivity, columns)
        val footerAdapter = LoadStateAdapter(characterAdapter::retry)
        binding.mainCharacterList.apply {
            this.layoutManager = layoutManager
            adapter = characterAdapter.withLoadStateFooter(footerAdapter)
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
            if (it.mediator?.refresh is LoadState.Error) {
                footerAdapter.loadState = it.refresh
            }
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

    private fun manageNetworkManager() {
        lifecycleScope.launchWhenStarted {
            ManageNetworkManager(networkRepository).invoke(lifecycle, ::shouldShowOfflineIcon)
        }
    }

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

    private fun setBiometricLogic() {
        CheckAuthenticationState(biometricRepository).invoke()
        binding.favoriteFab.setOnClickListener {
            SetBiometricAuthentication(biometricRepository).invoke(::navigateToFavorites)
        }
    }

    private fun navigateToFavorites() {
        val intent = Intent(this, FavoriteCharacterActivity::class.java)
        startActivity(intent)
    }
}
