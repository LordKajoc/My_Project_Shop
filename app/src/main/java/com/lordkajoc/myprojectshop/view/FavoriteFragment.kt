package com.lordkajoc.myprojectshop.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lordkajoc.myprojectshop.R
import com.lordkajoc.myprojectshop.databinding.FragmentFavoriteBinding
import com.lordkajoc.myprojectshop.view.adapter.CartAdapter
import com.lordkajoc.myprojectshop.view.adapter.FavAdapter
import com.lordkajoc.myprojectshop.viewmodel.CartViewModel
import com.lordkajoc.myprojectshop.viewmodel.FavoriteViewModel
import com.lordkajoc.myprojectshop.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    lateinit var binding: FragmentFavoriteBinding
    private lateinit var vmFav: FavoriteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFav()
    }

    fun getDataFav(){
         vmFav= ViewModelProvider(this).get(FavoriteViewModel::class.java)
        vmFav.getFav()
        vmFav.dataPostFav.observe(viewLifecycleOwner, Observer{
            binding.rvListFav.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false)
            binding.rvListFav.adapter = FavAdapter(it)
        })
    }

    override fun onStart() {
        super.onStart()
        getDataFav()
    }
}
