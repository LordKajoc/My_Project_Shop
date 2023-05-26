package com.lordkajoc.myprojectshop.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lordkajoc.myprojectshop.R
import com.lordkajoc.myprojectshop.databinding.ItemNewsBinding
import com.lordkajoc.myprojectshop.databinding.ItemProductBinding
import com.lordkajoc.myprojectshop.model.DataNewsResponseItem
import com.lordkajoc.myprojectshop.model.DataProductsResponseItem

class ProductAdapter(private var listProduct: List<DataProductsResponseItem>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindProduct(itemProduct: DataProductsResponseItem) {
            binding.product = itemProduct
            binding.cardView.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt("ID", itemProduct.id.toString().toInt())
                }
                it.findNavController().navigate(R.id.action_homeFragment_to_detailProductFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindProduct(listProduct[position])
        Glide.with(holder.itemView).load(listProduct[position].image).into(holder.binding.imgProduct)
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

}