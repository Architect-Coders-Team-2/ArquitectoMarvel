package com.architectcoders.arquitectomarvel.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.architectcoders.arquitectomarvel.BuildConfig.MARVEL_API_KEY
import com.architectcoders.arquitectomarvel.BuildConfig.MARVEL_PRIVATE_KEY
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ts = System.currentTimeMillis()
        val publicKey = MARVEL_API_KEY
        val privateKey = MARVEL_PRIVATE_KEY
        val hash = "$ts$privateKey$publicKey".md5
        initRecyclerView()

        lifecycleScope.launchWhenResumed {
            binding.progress.visibility = View.VISIBLE
            val characters = MarvelApiRest.service.getCharacters(ts, publicKey, hash)
            Timber.d("characters = $characters")
            Timber.d("characters.data?.results?.size ${characters.data?.results?.size}")
            (binding.mainHeroList.adapter as AdapterList).services =
                characters.data?.results ?: emptyList()
            binding.progress.visibility = View.GONE
        }
    }

    private fun initRecyclerView() {
        binding.mainHeroList.autoFitColumnsForGridLayout(resources.getDimension(R.dimen.avatar_width))
        binding.mainHeroList.adapter = AdapterList(this)
    }

    override fun onClick(mediaService: Result) {
        //TODO go to hero detail activity
    }
}