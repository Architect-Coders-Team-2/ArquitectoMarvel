package com.architectcoders.arquitectomarvel.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.architectcoders.arquitectomarvel.BuildConfig
import com.architectcoders.arquitectomarvel.databinding.ActivityMainBinding
import com.architectcoders.arquitectomarvel.model.MarvelApiRest
import com.architectcoders.arquitectomarvel.model.TAG
import com.architectcoders.arquitectomarvel.model.md5

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
            val personajes = MarvelApiRest.service.listPersonajes(ts,publicKey,hash)
            Log.d(TAG, "onCreate: $personajes")
        }

    }
}