package com.architectcoders.arquitectomarvel.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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
import com.architectcoders.arquitectomarvel.ui.common.NetworkLogicViewModel.*
import com.architectcoders.arquitectomarvel.ui.detail.CharacterDetailActivity
import com.architectcoders.arquitectomarvel.ui.favorite.FavoriteCharacterActivity
import com.architectcoders.arquitectomarvel.ui.main.MainViewModel.*
import com.architectcoders.arquitectomarvel.ui.main.MainViewModel.PasswordState.*
import com.architectcoders.arquitectomarvel.ui.main.PasswordDialogFragment.*
import com.architectcoders.arquitectomarvel.ui.main.pagination.CharacterAdapter
import com.architectcoders.arquitectomarvel.ui.main.pagination.LoadStateAdapter
import com.architectcoders.domain.character.Character
import com.architectcoders.usecases.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var checkAuthenticationState: CheckAuthenticationState

    @Inject
    lateinit var checkIfUserIsAlreadyAuthenticated: CheckIfUserIsAlreadyAuthenticated

    @Inject
    lateinit var saveAuthenticationState: SaveAuthenticationState

    @Inject
    lateinit var canUserUseBiometricAuthentication: CanUserUseBiometricAuthentication

    @Inject
    lateinit var setBiometricAuthentication: SetBiometricAuthentication

    @Inject
    lateinit var characterAdapter: CharacterAdapter

    private lateinit var binding: ActivityMainBinding
    private var menuItem: MenuItem? = null
    private val mainViewModel: MainViewModel by viewModels()
    private val networkLogicViewModel: NetworkLogicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()
        collectLatestPager()
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
        characterAdapter.listener = ::navigateTo
        lifecycleScope.launchWhenStarted {
            networkLogicViewModel.uiNetworkModel.collect {
                updateUi(it)
            }
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

    private fun updateUi(uiNetworkModel: UiNetworkModel) {
        if (uiNetworkModel is UiNetworkModel.SetNetworkAvailability) {
            shouldShowOfflineIcon(uiNetworkModel.isAvailable)
        }
    }

    /**
     * If the visibility suddenly changes when we had already changed it a few seconds ago,
     * we need to wait to avoid race conditions.
     */
    private fun shouldShowOfflineIcon(internetAvailable: Boolean) {
        lifecycleScope.launchWhenStarted {
            delay(TIME_MILLIS_DELAY_TO_AVOID_TOOLBAR_RACE_CONDITION)
            menuItem?.isVisible = !internetAvailable
        }
    }

    @ExperimentalPagingApi
    private fun collectLatestPager() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.pager.collectLatest {
                characterAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun setBiometricLogic() {
        checkAuthenticationState.invoke()
        initPasswordStateCollector()
        binding.favoriteFab.setOnClickListener {
            when {
                checkIfUserIsAlreadyAuthenticated.invoke() -> startActivity<FavoriteCharacterActivity> {}
                canUserUseBiometricAuthentication.invoke() -> {
                    setBiometricAuthentication.invoke(
                        onFail = {
                            Toast.makeText(
                                this, getString(R.string.something_wrong),
                                Toast.LENGTH_LONG
                            ).show()
                        },
                        onSuccess = {
                            saveAuthenticationState.invoke(true)
                            startActivity<FavoriteCharacterActivity> {}
                        }
                    )
                }
                else -> {
                    mainViewModel.ifDeviceNeitherHaveBiometricLoginNorPassword(::showDialogWithPasswordState)
                }
            }
        }
    }

    private fun initPasswordStateCollector() {
        lifecycleScope.launchWhenCreated {
            mainViewModel.passwordState.collect {
                when (it) {
                    INITIAL_STATE -> Unit
                    SAVE_PASSWORD, REQUEST_PASSWORD, REQUEST_HINT -> showPasswordDialogFragment(it)
                    SUCCESSFUL -> {
                        saveAuthenticationState.invoke(true)
                        startActivity<FavoriteCharacterActivity> {}
                    }
                }
            }
        }
    }

    private fun showDialogWithPasswordState(isPasswordStored: Boolean) {
        if (isPasswordStored) {
            showPasswordDialogFragment(REQUEST_PASSWORD)
        } else {
            showPasswordDialogFragment(SAVE_PASSWORD)
        }
    }

    private fun showPasswordDialogFragment(passwordState: PasswordState) {
        val passwordDialogFragment = PasswordDialogFragment.getInstance(passwordState)
        passwordDialogFragment.show(
            supportFragmentManager,
            PasswordDialogFragment::class.java.canonicalName
        )
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

    override fun onStop() {
        super.onStop()
        networkLogicViewModel.clearNetworks()
    }
}
