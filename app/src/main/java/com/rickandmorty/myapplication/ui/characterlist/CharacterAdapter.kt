package com.rickandmorty.myapplication.ui.characterlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rick_and_morty.app.databinding.ItemCharacterListBinding
import com.rick_and_morty.domain.models.Character
import com.rickandmorty.myapplication.base.BaseAdapter
import javax.inject.Inject

class CharacterAdapter @Inject constructor(
    private val glide: RequestManager
) : BaseAdapter<Character>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override val differ = AsyncListDiffer(this, diffCallback)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemCharacterListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    inner class CharacterViewHolder(private val binding: ItemCharacterListBinding) :
        RecyclerView.ViewHolder(binding.root), Binder<Character> {
        override fun bind(item: Character) {
            binding.apply {
                textViewCharacterName.text = item.name
                glide.load(item.image).into(imageViewCharacter)
                root.setOnClickListener {
                    onItemClickListener?.let { itemClick ->
                        itemClick(item)
                    }
                }
                textViewStatus.text = "${item.status} - ${item.species}"
                textViewKnownLocation.text = item.characterLocation.name
            }
        }
    }
}
