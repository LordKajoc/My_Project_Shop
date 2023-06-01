package com.lordkajoc.myprojectshop.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lordkajoc.myprojectshop.R
import com.lordkajoc.myprojectshop.databinding.CartItemBinding
import com.lordkajoc.myprojectshop.databinding.ItemFavoriteBinding
import com.lordkajoc.myprojectshop.model.DataCartResponseItem
import com.lordkajoc.myprojectshop.model.DataFavProductResponseItem

class FavouriteAdapter(private var listFav: List<DataFavProductResponseItem>) :
    RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindFav(itemCart: DataFavProductResponseItem) {
            binding.favorite= itemCart
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFav.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindFav(listFav[position])
        Glide.with(holder.itemView)
            .load(listFav[position].productImage)
            .into(holder.binding.imgProduct)

    }
}