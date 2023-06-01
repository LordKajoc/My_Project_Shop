package com.lordkajoc.myprojectshop.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lordkajoc.myprojectshop.databinding.CartItemBinding
import com.lordkajoc.myprojectshop.databinding.ItemTranshistoryBinding
import com.lordkajoc.myprojectshop.model.DataCartResponseItem
import com.lordkajoc.myprojectshop.model.DataTransHistory

class TransHistoryAdapter (private var listTrans: List<DataTransHistory>) :
    RecyclerView.Adapter<TransHistoryAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemTranshistoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindTrans(itemCart: DataTransHistory) {
            binding.trans= itemCart
//            binding.cvCart.setOnClickListener {
//                val bundle = Bundle().apply {
//                    putInt("ID", itemCart.idCart.toInt())
//                }
//                it.findNavController().navigate(R.id.action_detailProductFragment_to_cartFragment, bundle)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemTranshistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTrans.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTrans(listTrans[position])
        Glide.with(holder.itemView)
            .load(listTrans[position].productImage)
            .into(holder.binding.imgProductTrans)

    }
}