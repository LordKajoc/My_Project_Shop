package com.lordkajoc.myprojectshop.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lordkajoc.myprojectshop.databinding.ItemCartBinding
import com.lordkajoc.myprojectshop.model.DataCartResponseItem
import com.lordkajoc.myprojectshop.viewmodel.CartViewModel

class CartAdapter(private var listCart: List<DataCartResponseItem>, private val cartVM:CartViewModel) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {


    class ViewHolder(var binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root){

        fun bindCart(itemCart: DataCartResponseItem) {
            binding.cart = itemCart
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        holder.binding.imgDeletecart.setOnClickListener {
            val cartItem = listCart[position]
            cartVM.deleteCartProducts(cartItem.userId, cartItem.idCart)
            Toast.makeText(holder.itemView.context, "Item Telah dihapus", Toast.LENGTH_SHORT).show()
        }
    }

}