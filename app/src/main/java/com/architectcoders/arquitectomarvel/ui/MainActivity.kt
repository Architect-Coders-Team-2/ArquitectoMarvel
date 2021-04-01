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
import com.architectcoders.arquitectomarvel.model.database.ResultDatabase
import com.architectcoders.arquitectomarvel.model.database.dbItemComics
import com.architectcoders.arquitectomarvel.model.database.dbObject
import com.architectcoders.arquitectomarvel.model.database.relations.ResultWithItemsComics
import com.architectcoders.arquitectomarvel.model.database.relations.toListResult
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
        val publicKey = BuildConfig.MARVEL_API_KEY
        val privateKey = BuildConfig.MARVEL_PRIVATE_KEY
        val hash = "$ts$privateKey$publicKey".md5
        initRecyclerView()

        lifecycleScope.launchWhenResumed {
            val characters = MarvelApiRest.service.getCharacters(ts, publicKey, hash)

            val resultsRemote = characters.data?.results?: emptyList()

            val dao = ResultDatabase.getInstance(this@MainActivity).resultDao
            resultsRemote.forEach {  result ->
                dao.insertResult(result.dbObject)
                val colectionUri = result.comics.collectionURI
                result.comics.items.forEach { item ->
                    dao.insertComics(item.dbItemComics(colectionUri))
                }
            }

            val listaLocal = mutableListOf<ResultWithItemsComics>()
            val tmpResult = dao.getResults()
            tmpResult.forEach {
                val itemForListaLocal = dao.getResultWithItemsComics(it.comicsCollectionURI)
                listaLocal.addAll(itemForListaLocal)
            }

            (binding.mainHeroList.adapter as AdapterList).services = listaLocal.toListResult
        }
    }

    private fun initRecyclerView() {
        binding.mainHeroList.autoFitColumnsForGridLayout(resources.getDimension(R.dimen.avatar_width))
        binding.mainHeroList.adapter = AdapterList(this)
    }

    override fun onClick(mediaService: Result) {
        //TODO go to hero detail activity
        Timber.d("qq_MainActivity.onClick: ${mediaService.comics}")
    }
}