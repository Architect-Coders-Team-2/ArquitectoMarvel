package com.architectcoders.arquitectomarvel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.architectcoders.arquitectomarvel.databinding.ActivityMainBinding
import com.architectcoders.arquitectomarvel.model.MarvelApiRest
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.model.md5
import com.architectcoders.arquitectomarvel.ui.main.AdapterList
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ts = System.currentTimeMillis()
        val publicKey = BuildConfig.MARVEL_API_KEY
        val privateKey = BuildConfig.MARVEL_PRIVATE_KEY
        val hash = "$ts$privateKey$publicKey".md5


        lifecycleScope.launchWhenResumed {
            val characters = MarvelApiRest.service.getCharacters(ts, publicKey, hash)
            Timber.d("characters = $characters")
            Timber.d("characters.data?.results?.size ${characters.data?.results?.size}")

            showData(characters.data!!.results!!)

        }

    }

    fun showData(heroList: List<Result>?) {

        binding.mainHeroList.apply {
            val LayoutManager = GridLayoutManager(this@MainActivity, 5)

            binding.mainHeroList.layoutManager = LayoutManager
         //   binding.mainHeroList.itemAnimator = DefaultItemAnimator()
            adapter = AdapterList(this@MainActivity, heroList)
        }

    }


}