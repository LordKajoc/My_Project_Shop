package com.lordkajoc.myprojectshop.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lordkajoc.myprojectshop.R
import com.lordkajoc.myprojectshop.data.network.ApiService
import com.lordkajoc.myprojectshop.databinding.CartItemBinding
import com.lordkajoc.myprojectshop.databinding.ItemNewsBinding
import com.lordkajoc.myprojectshop.databinding.ItemProductBinding
import com.lordkajoc.myprojectshop.model.DataCart
import com.lordkajoc.myprojectshop.model.DataCartResponseItem
import com.lordkajoc.myprojectshop.model.DataNewsResponseItem
import com.lordkajoc.myprojectshop.view.CartFragment
import com.lordkajoc.myprojectshop.viewmodel.CartViewModel

class CartAdapter(private var listCart: List<DataCartResponseItem>, private val cartVM: CartViewModel) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private var onDeleteClickListener: ((DataCartResponseItem) -> Unit)? = null
    class ViewHolder(var binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindCart(itemCart: DataCartResponseItem) {
            binding.cart= itemCart
            binding.cvCart.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt("ID", itemCart.idCart.toInt())
                }
                it.findNavController().navigate(R.id.action_detailProductFragment_to_cartFragment, bundle)
            }
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
        holder.binding.deleteCart.setOnClickListener {
            val cartItem = listCart[position]
            cartVM.deleteCart(listCart[position].userId, listCart[position].idCart)
//            refreshData(listCart)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, listCart.size)
                Toast.makeText(
                    holder.itemView.context,
                    "Sudah Terhapus dari Keranjang",
                    Toast.LENGTH_SHORT
                ).show()
        }

    }

    fun refreshData(newListCart: List<DataCartResponseItem>) {
        listCart = newListCart
        notifyDataSetChanged()
    }

    fun <T> MutableList<T>.removeAt(index: Int): T {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException("Index: $index, Size: $size")
        }
        return removeAt(index)
    }












}