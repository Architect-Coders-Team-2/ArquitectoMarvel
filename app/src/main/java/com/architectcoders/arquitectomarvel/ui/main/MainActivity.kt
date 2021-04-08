package com.architectcoders.arquitectomarvel.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.ActivityMainBinding
import com.architectcoders.arquitectomarvel.model.Repository
import com.architectcoders.arquitectomarvel.model.autoFitColumnsForGridLayout
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.ui.common.NetworkMonitor
import com.architectcoders.arquitectomarvel.ui.main.AdapterList
import com.architectcoders.arquitectomarvel.ui.main.MainPresenter
import timber.log.Timber

class MainActivity : AppCompatActivity(), MainPresenter.View {

    private lateinit var binding: ActivityMainBinding
    private val presenter by lazy { MainPresenter(Repository(this)) }
    private val adapterList by lazy { AdapterList(presenter::onResultClick) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.onCreate(this)
        NetworkMonitor(application).startNetworkCallback()
    }

    override fun initViews() {
        binding.mainHeroList.autoFitColumnsForGridLayout(resources.getDimension(R.dimen.avatar_width))
        binding.mainHeroList.adapter = adapterList
    }

    override fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progress.visibility = View.GONE
    }

    override fun updateData(list: List<Result>) {
        adapterList.submitList(list)
    }

    override fun showToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    override fun navigateTo(result: Result) {
        Timber.d("qq_MainActivity.navigateTo: ${result.comics.collectionURI}")
    }

    override fun onDestroy() {
        presenter.onDestroy()
        NetworkMonitor(application).stopNetworkCallback()
        super.onDestroy()
    }
}