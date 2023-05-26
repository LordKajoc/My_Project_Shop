package com.lordkajoc.myprojectshop.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lordkajoc.myprojectshop.R
import com.lordkajoc.myprojectshop.databinding.ItemNewsBinding
import com.lordkajoc.myprojectshop.model.DataNewsResponseItem

class NewsAdapter(private var listNews: List<DataNewsResponseItem>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        fun bindFilms(itemNews: DataNewsResponseItem) {
            binding.news = itemNews

            binding.cardView.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt("ID", itemNews.idNews.toString().toInt())
                }
                it.findNavController().navigate(R.id.action_homeFragment_to_detailNewsFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindFilms(listNews[position])
        Glide.with(holder.itemView).load(listNews[position].newsImage).into(holder.binding.imgNews)
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

}