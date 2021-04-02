package com.architectcoders.arquitectomarvel.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.architectcoders.arquitectomarvel.BuildConfig
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.ActivityMainBinding
import com.architectcoders.arquitectomarvel.model.MarvelApiRest
import com.architectcoders.arquitectomarvel.model.autoFitColumnsForGridLayout
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.model.md5
import com.architectcoders.arquitectomarvel.ui.main.AdapterList
import com.architectcoders.arquitectomarvel.ui.main.ClickListener
import timber.log.Timber


class MainActivity : AppCompatActivity(), ClickListener {

    private lateinit var binding: ActivityMainBinding
    private val adapterList by lazy { AdapterList(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ts = System.currentTimeMillis()
        val publicKey = BuildConfig.MARVEL_API_KEY
        val privateKey = BuildConfig.MARVEL_PRIVATE_KEY
        val hash = "$ts$privateKey$publicKey".md5
        initRecyclerView()

        lifecycleScope.launchWhenResumed {
            val characters = MarvelApiRest.service.getCharacters(ts, publicKey, hash)
            val results = characters.data?.results ?: emptyList()
            adapterList.submitList(results)
        }
    }

    private fun initRecyclerView() {
        binding.mainHeroList.autoFitColumnsForGridLayout(resources.getDimension(R.dimen.avatar_width))
        binding.mainHeroList.adapter = adapterList
    }

    override fun onClick(mediaService: Result) {
        //TODO go to hero detail activity
    }
}