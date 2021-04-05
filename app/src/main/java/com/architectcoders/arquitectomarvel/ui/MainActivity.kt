package com.architectcoders.arquitectomarvel.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.architectcoders.arquitectomarvel.BuildConfig.MARVEL_API_KEY
import com.architectcoders.arquitectomarvel.BuildConfig.MARVEL_PRIVATE_KEY
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
import java.net.UnknownHostException

class MainActivity : AppCompatActivity(), ClickListener {

    private lateinit var binding: ActivityMainBinding
    private val adapterList by lazy { AdapterList(this) }

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

            val dao = ResultDatabase.getInstance(this@MainActivity).resultDao
            try {
                val characters = MarvelApiRest.service.getCharacters(ts, publicKey, hash)
                val resultsRemote = characters.characterData?.results ?: emptyList()

                resultsRemote.forEach { result ->
                    dao.insertResult(result.dbObject)
                    val colectionUri = result.comics.collectionURI
                    result.comics.items.forEach { item ->
                        dao.insertComics(item.dbItemComics(colectionUri))
                    }
                }
            } catch (e: UnknownHostException) {
                Toast.makeText(applicationContext, "Sin conexión (DB data)", Toast.LENGTH_SHORT)
                    .show()
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "$e", Toast.LENGTH_SHORT).show()
            }

            val listaLocal = mutableListOf<ResultWithItemsComics>()
            val tmpResult = dao.getResults()
            tmpResult.forEach {
                val itemForListaLocal = dao.getResultWithItemsComics(it.comicsCollectionURI)
                listaLocal.addAll(itemForListaLocal)
            }

            adapterList.submitList(listaLocal.toListResult)
            binding.progress.visibility = View.GONE
        }
    }

    private fun initRecyclerView() {
        binding.mainHeroList.autoFitColumnsForGridLayout(resources.getDimension(R.dimen.avatar_width))
        binding.mainHeroList.adapter = adapterList
    }

    override fun onClick(mediaService: Result) {
        // TODO go to hero detail activity
        Timber.d("qq_MainActivity.onClick: ${mediaService.comics}")
    }
}
