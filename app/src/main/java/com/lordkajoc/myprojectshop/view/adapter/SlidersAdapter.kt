package com.lordkajoc.myprojectshop.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lordkajoc.myprojectshop.databinding.ItemSliderBinding
import com.lordkajoc.myprojectshop.model.DataSlidersResponseItem

class SlidersAdapter (var listSlider : List<DataSlidersResponseItem>) : RecyclerView.Adapter<SlidersAdapter.ViewHolder>() {
    class ViewHolder (var binding: ItemSliderBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(listSlider[position].image).into(holder.binding.imageSlider)

    }
    override fun getItemCount(): Int {
        return listSlider.size
    }
}