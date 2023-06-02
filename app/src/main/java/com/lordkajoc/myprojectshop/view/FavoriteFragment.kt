package com.lordkajoc.myprojectshop.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lordkajoc.myprojectshop.R
import com.lordkajoc.myprojectshop.databinding.FragmentCartBinding
import com.lordkajoc.myprojectshop.databinding.FragmentFavoriteBinding
import com.lordkajoc.myprojectshop.view.adapter.CartAdapter
import com.lordkajoc.myprojectshop.view.adapter.FavouriteAdapter
import com.lordkajoc.myprojectshop.viewmodel.CartViewModel
import com.lordkajoc.myprojectshop.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    lateinit var binding: FragmentFavoriteBinding
    private lateinit var vmFav : FavoriteViewModel
    private lateinit var idUser : String
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
        idUser = sharedPreferences.getString("id","").toString()
        if (sharedPreferences.getString("id", "")!!.isEmpty()) {
            binding.rvListFav.visibility = View.GONE
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Login")
                .setMessage("Anda Belum Login")
                .setCancelable(false)
                .setNegativeButton("Cancel") { dialog, which ->
                    // Respond to negative button press
                    findNavController().navigate(R.id.action_favoriteFragment_to_homeFragment)
                }
                .setPositiveButton("Login") { dialog, which ->
                    // Respond to positive button press
                    findNavController().navigate(R.id.action_favoriteFragment_to_loginFragment)
                }
                .show()
        } else if (sharedPreferences.getString("id", "")!!.isNotEmpty()) {
            getDataFav(idUser)
        }
    }

    fun getDataFav(userId: String){
        vmFav = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        vmFav.getFav(userId)
        vmFav.dataFav.observe(viewLifecycleOwner, Observer{
            binding.rvListFav.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false)
            binding.rvListFav.adapter = FavouriteAdapter(it)
        })
    }

    override fun onStart() {
        super.onStart()
        getDataFav(idUser)
    }
}