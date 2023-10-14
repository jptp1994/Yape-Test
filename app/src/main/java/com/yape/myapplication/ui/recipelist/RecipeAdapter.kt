package com.yape.myapplication.ui.recipelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.yape.app.databinding.ItemRecipeListBinding
import com.yape.domain.models.Recipe
import com.yape.myapplication.base.BaseAdapter
import javax.inject.Inject

class RecipeAdapter @Inject constructor(
    private val glide: RequestManager
) : BaseAdapter<Recipe>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override val differ = AsyncListDiffer(this, diffCallback)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemRecipeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    inner class RecipeViewHolder(private val binding: ItemRecipeListBinding) :
        RecyclerView.ViewHolder(binding.root), Binder<Recipe> {
        override fun bind(item: Recipe) {
            binding.apply {
                textViewRecipeName.text = item.name
                glide.load(item.image).into(imageViewRecipe)
                root.setOnClickListener {
                    onItemClickListener?.let { itemClick ->
                        itemClick(item)
                    }
                }
                textViewTime.text = item.time
                textViewDifficult.text = item.difficult
            }
        }
    }
}
