package com.lordkajoc.myprojectshop.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lordkajoc.myprojectshop.databinding.CartItemBinding
import com.lordkajoc.myprojectshop.model.DataCartResponseItem
import com.lordkajoc.myprojectshop.model.DataNewsResponseItem

class CartAdapter(private var listCart: List<DataCartResponseItem>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder(var binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindCart(itemCart: DataCartResponseItem) {
            binding.cart= itemCart
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listCart.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindCart(listCart[position])
        Glide.with(holder.itemView)
            .load(listCart[position].productImage)
            .into(holder.binding.imgProductCart)

    }
}