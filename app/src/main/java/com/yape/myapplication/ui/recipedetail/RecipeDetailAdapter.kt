package com.yape.myapplication.ui.recipedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.yape.app.databinding.ItemRecipeIngredientListBinding
import com.yape.domain.models.RecipeIngredient
import com.yape.myapplication.base.BaseAdapter
import javax.inject.Inject

class RecipeDetailAdapter @Inject constructor(
    private val glide: RequestManager
) : BaseAdapter<RecipeIngredient>() {

    private val diffCallback = object : DiffUtil.ItemCallback<RecipeIngredient>() {
        override fun areItemsTheSame(oldItem: RecipeIngredient, newItem: RecipeIngredient): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: RecipeIngredient, newItem: RecipeIngredient): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override val differ = AsyncListDiffer(this, diffCallback)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemRecipeIngredientListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    inner class RecipeViewHolder(private val binding: ItemRecipeIngredientListBinding) :
        RecyclerView.ViewHolder(binding.root), Binder<RecipeIngredient> {
        override fun bind(item: RecipeIngredient) {
            binding.apply {
                textViewRecipeName.text = item.name
                glide.load(item.image).into(imageViewRecipe)
            }
        }
    }
}
