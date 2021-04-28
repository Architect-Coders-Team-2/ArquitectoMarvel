package com.architectcoders.arquitectomarvel.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.transition.TransitionInflater
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.FragmentMainBinding
import com.architectcoders.arquitectomarvel.model.*
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.ui.common.Event

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: AdapterList
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = container?.bindingInflate(R.layout.fragment_main)
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainHeroList.autoFitColumnsForGridLayout(resources.getDimension(R.dimen.avatar_width))
        navController = view.findNavController()
        viewModel = getViewModel { MainViewModel(Repository(binding.root.context)) }
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        adapter = AdapterList(::navigateTo)
        binding.mainHeroList.adapter = adapter
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            context?.toast(it)
        }
        // When user hits back button transition takes backward
        postponeEnterTransition()
        binding.mainHeroList.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun navigateTo(result: Result, view: View) {
        Event(result).getContentIfNotHandled()?.let { resultValue ->
            val name = resultValue.name ?: ""
            val navigationExtras = FragmentNavigatorExtras(view to name)
            val action = MainFragmentDirections.actionMainFragmentToHeroDetailFragment(resultValue)
            navController.navigate(action, navigationExtras)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
