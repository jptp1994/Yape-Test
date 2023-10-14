package com.yape.myapplication.ui.recipelist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yape.app.R
import com.yape.app.databinding.FragmentRecipeListBinding
import com.yape.myapplication.base.BaseFragment
import com.yape.myapplication.extension.observe
import com.yape.ui.viewmodel.BaseViewModel
import com.yape.ui.viewmodel.RecipeListViewModel
import com.yape.ui.viewmodel.RecipeUIModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : BaseFragment<FragmentRecipeListBinding, BaseViewModel>() {

    override fun getViewBinding(): FragmentRecipeListBinding =
        FragmentRecipeListBinding.inflate(layoutInflater)

    override val viewModel: RecipeListViewModel by viewModels()

    @Inject
    lateinit var recipeAdapter: RecipeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isFavorite =
            (findNavController().currentDestination?.label == getString(R.string.menu_favorites))
        viewModel.getRecipe(isFavorite)
        observe(viewModel.getRecipe(), ::onViewStateChange)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerViewRecipes.apply {
            adapter = recipeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        recipeAdapter.setItemClickListener { recipe ->
            findNavController().navigate(
                RecipeListFragmentDirections.actionRecipeListFragmentToRecipeDetailFragment(
                    recipe.id
                )
            )
        }
    }

    private fun onViewStateChange(event: RecipeUIModel) {
        if (event.isRedelivered) return
        when (event) {
            is RecipeUIModel.Error -> handleErrorMessage(event.error)
            is RecipeUIModel.Loading -> handleLoading(true)
            is RecipeUIModel.Success -> {
                handleLoading(false)
                event.data.let {
                    recipeAdapter.list = it
                }
            }
        }
    }
}
