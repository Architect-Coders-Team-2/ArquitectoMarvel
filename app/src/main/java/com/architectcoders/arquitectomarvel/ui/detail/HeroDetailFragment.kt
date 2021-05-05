package com.architectcoders.arquitectomarvel.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.FragmentHeroDetailBinding
import com.architectcoders.arquitectomarvel.model.*
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.model.database.DetailedComicEntity
import com.architectcoders.arquitectomarvel.model.database.toDetailedComicEntityList
import com.architectcoders.arquitectomarvel.ui.NavHostActivity
import com.architectcoders.arquitectomarvel.ui.detail.HeroDetailViewModel.UiModel
import java.util.concurrent.TimeUnit
import com.architectcoders.arquitectomarvel.model.comics.Result as ComicResult

class HeroDetailFragment : Fragment() {

    private var _binding: FragmentHeroDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var heroDetailViewModel: HeroDetailViewModel
    private val adapter by lazy { ComicAdapter() }
    private lateinit var selectedCharacter: Result
    private var isCharacterFavorite = false
    private val args: HeroDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeroDetailBinding.inflate(LayoutInflater.from(context), container, false)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contentHeroDetail.comicList.adapter = adapter
        heroDetailViewModel = getViewModel { HeroDetailViewModel(Repository(binding.root.context)) }
        heroDetailViewModel.model.observe(viewLifecycleOwner, Observer(::updateUi))
        binding.headerHeroImage.transitionName = args.result.name
    }

    private fun updateUi(model: UiModel) {
        binding.contentHeroDetail.progress.isVisible = model is UiModel.Loading
        when (model) {
            is UiModel.SetHeroDetails -> setHeroDetails(model.onHeroShown)
            is UiModel.ShowToast -> binding.root.context.toast(model.msgResource)
            is UiModel.UpdateFAB -> updateFAB(model.isCharacterFavorite, model.listener)
            is UiModel.UpdateComics -> updateComics(model.comicList)
        }
    }

    private fun setHeroDetails(onHeroShown: (Int) -> Unit) {
        this.selectedCharacter = args.result
        binding.headerHeroImage.loadUrl(
            selectedCharacter.thumbnail?.path,
            selectedCharacter.thumbnail?.extension
        )
        binding.toolbar.title = selectedCharacter.name ?: EMPTY_TEXT
        binding.toolbarLayout.title = selectedCharacter.name ?: EMPTY_TEXT
        binding.contentHeroDetail.heroContent.text =
            if (selectedCharacter.description.isNullOrBlank()) {
                getString(R.string.content_not_available)
            } else {
                selectedCharacter.description
            }
        onHeroShown(selectedCharacter.id)
    }

    /**
     * Initially saves if the character is favorite, updates the FAB image and sets the click listener
     * if the character is available
     */
    private fun updateFAB(
        isCharacterFavorite: Boolean,
        listener: (
            selectedHero: Result,
            comicList: MutableList<DetailedComicEntity>,
            isCharacterFavorite: Boolean,
        ) -> Unit,
    ) {
        this.isCharacterFavorite = isCharacterFavorite
        binding.fab.loadImage(
            android.R.drawable.star_on,
            android.R.drawable.star_off,
            isCharacterFavorite
        )
        binding.fab.setOnClickListener {
            selectedCharacter.let { character ->
                this.isCharacterFavorite = this.isCharacterFavorite.not()
                binding.fab.loadImage(
                    android.R.drawable.star_on,
                    android.R.drawable.star_off,
                    this.isCharacterFavorite
                )
                listener(character, adapter.currentList, this.isCharacterFavorite)
            }
        }
    }

    private fun updateComics(comicList: List<ComicResult>) {
        if (comicList.isEmpty()) {
            binding.contentHeroDetail.noComics.isVisible = true
        }
        adapter.submitList(comicList.toDetailedComicEntityList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
