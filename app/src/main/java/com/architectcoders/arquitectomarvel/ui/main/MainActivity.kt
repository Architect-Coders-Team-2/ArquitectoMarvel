package com.architectcoders.arquitectomarvel.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.*
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.ActivityMainBinding
import com.architectcoders.arquitectomarvel.model.*
import com.architectcoders.arquitectomarvel.model.database.ResultDatabase
import com.architectcoders.arquitectomarvel.model.database.RoomDataSource
import com.architectcoders.arquitectomarvel.ui.detail.HeroDetailActivity
import com.architectcoders.module.data.CredentialsApiRepository
import com.architectcoders.module.data.LocalDataSource
import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.data.RemoteDataSource
import com.architectcoders.module.usescases.UseCaseGetCharactersRemote
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private fun getRepository(context: Context): MarvelRepository {
        val roomDataSource: LocalDataSource = RoomDataSource(ResultDatabase.getInstance(context))
        val credentialsApiRepository: CredentialsApiRepository = CredentialApiRepositoryImpl()
        val retrofitDataSource: RemoteDataSource = RetrofitDataSource(credentialsApiRepository)
        return MarvelRepository(
            roomDataSource,
            retrofitDataSource,
            credentialsApiRepository
        )
    }

    lateinit var useCaseGetCharactersRemote: UseCaseGetCharactersRemote

    private lateinit var viewModel: MainViewModel


    private val adapter: AdapterList by lazy {
        AdapterList(viewModel::onResultClick)
    }
    private var viewItem: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = getRepository(applicationContext)
        useCaseGetCharactersRemote = UseCaseGetCharactersRemote(repo)
        val vmf = VMFuseCaseGetCharactersRemote(useCaseGetCharactersRemote)
        viewModel = ViewModelProvider(this, vmf).get(MainViewModel::class.java)

        setUpViews()
        observersViewModel()
    }

    private fun setUpViews() {
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