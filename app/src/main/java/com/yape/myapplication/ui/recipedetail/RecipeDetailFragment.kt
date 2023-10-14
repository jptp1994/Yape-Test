package com.yape.myapplication.ui.recipedetail

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.yape.app.R
import com.yape.app.databinding.FragmentRecipeDetailBinding
import com.yape.myapplication.base.BaseFragment
import com.yape.myapplication.extension.getImageDifficult
import com.yape.myapplication.extension.observe
import com.yape.myapplication.extension.showSnackBar
import com.yape.ui.viewmodel.BaseViewModel
import com.yape.ui.viewmodel.Bookmark
import com.yape.ui.viewmodel.RecipeDetailUIModel
import com.yape.ui.viewmodel.RecipeDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeDetailFragment : BaseFragment<FragmentRecipeDetailBinding, BaseViewModel>(), OnMapReadyCallback {

    override fun getViewBinding(): FragmentRecipeDetailBinding =
        FragmentRecipeDetailBinding.inflate(layoutInflater)

    override val viewModel: RecipeDetailViewModel by viewModels()

    private val args: RecipeDetailFragmentArgs by navArgs()

    private lateinit var map:GoogleMap

    private lateinit var latLng: LatLng

    @Inject
    lateinit var recipeDetailAdapter: RecipeDetailAdapter

    @Inject
    lateinit var glide: RequestManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.getRecipe(), ::onViewStateChange)
        viewModel.getRecipeDetail(args.recipeId)
        setUiChangeListeners()

        setMap()
        initRecyclerView()
    }

    private fun setMap() {
        val mapFragment= childFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    private fun initRecyclerView() {
        binding.rvIngredientList.apply {
            adapter = recipeDetailAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUiChangeListeners() {
        binding.checkBoxBookmark.setOnCheckedChangeListener { view, isChecked ->
            if (!binding.checkBoxBookmark.isPressed) {
                return@setOnCheckedChangeListener
            }
            if (isChecked)
                viewModel.setBookmarkRecipe(view.tag.toString().toLong())
            else
                viewModel.setUnBookmarkRecipe(view.tag.toString().toLong())
        }
    }

    private fun onViewStateChange(result: RecipeDetailUIModel) {
        if (result.isRedelivered) return
        when (result) {
            is RecipeDetailUIModel.BookMarkStatus -> {
                when (result.bookmark) {
                    Bookmark.BOOKMARK ->
                        if (result.status) {
                            showSnackBar(binding.rootView, getString(R.string.bookmark_success))
                        } else {
                            handleErrorMessage(getString(R.string.bookmark_error))
                        }
                    Bookmark.UN_BOOKMARK ->
                        if (result.status) {
                            showSnackBar(
                                binding.rootView,
                                getString(R.string.un_bookmark_success)
                            )
                        } else {
                            handleErrorMessage(getString(R.string.bookmark_error))
                        }
                }
            }
            is RecipeDetailUIModel.Error -> handleErrorMessage(result.error)
            RecipeDetailUIModel.Loading -> handleLoading(true)
            is RecipeDetailUIModel.Success -> {
                handleLoading(false)
                result.data.let { recipe ->
                    binding.apply {
                        textViewRecipeName.text = recipe.name
                        glide.load(recipe.image).into(imageViewRecipe)
                        checkBoxBookmark.tag = recipe.id
                        checkBoxBookmark.isChecked = recipe.isBookMarked
                        tvDifficultText.text = recipe.difficult
                        imvDifficult.setImageDrawable(ContextCompat.getDrawable(requireContext(),recipe.difficult.getImageDifficult()))
                        tvTime.text = recipe.time
                        recipeDetailAdapter.list = recipe.ingredients
                        tvDescription.text = recipe.description
                        latLng = LatLng(
                            recipe.recipeLocation.latitude.toDouble(),
                            recipe.recipeLocation.longitude.toDouble())
                    }
                }
            }
        }
    }

    private fun createMarker() {
        val marker:MarkerOptions = MarkerOptions().position(latLng)
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(latLng,16F)
        )
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
    }
}
