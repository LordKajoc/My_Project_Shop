package com.lordkajoc.myprojectshop.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.lordkajoc.myprojectshop.R
import com.lordkajoc.myprojectshop.databinding.FragmentDetailProductBinding
import com.lordkajoc.myprojectshop.model.DataDetailProductItem
import com.lordkajoc.myprojectshop.model.DataFav
import com.lordkajoc.myprojectshop.model.DataNewsResponseItem
import com.lordkajoc.myprojectshop.viewmodel.FavoriteViewModel
import com.lordkajoc.myprojectshop.viewmodel.HomeViewModel
import com.lordkajoc.myprojectshop.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailProductFragment : Fragment() {

    private lateinit var binding: FragmentDetailProductBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favViewModel: FavoriteViewModel
    private lateinit var selectedProduct: DataFav
    private var isFavorite by Delegates.notNull<Boolean>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        val id = arguments?.getInt("ID")
        if (id != null) {
            viewModel.getProductById(id)
            observeDetailProduct()
            setFavoriteListener()
            checkFavorite(id)
            //test crashlytics
            binding.btnCrashdetail.setOnClickListener {
                throw RuntimeException("Test Crash") // Force a crash
            }
        }
    }

    private fun observeDetailProduct() {
        viewModel.detailProduct.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    binding.tvNamaproductdetail.text = it.name.toString()
                    binding.tvReleaseproductdetail.text = "Release: " + it.createdAt.toString()
                    Glide.with(requireContext())
                        .load("${it.productImage}")
                        .into(binding.ivProductimagedetail)
                    //        binding.tvSinopsisfilmdetail.text = """Overview:
//            ${getfilm.overview}
//        """.trimIndent()
                    binding.tvDescriptionproductdetail.text = """Description:
                        |
                    """.trimMargin() + it.description.toString()
                    selectedProduct = DataFav(
                        it.name!!,
                        it.price!!,
                        it.productImage!!
                    )
                }
            }
        }
    }

    private fun setFavoriteListener() {
        isFavorite = true
        binding.icFav.apply {
            setOnClickListener {
                isFavorite = if (!isFavorite) {
                    addToFavorite()
                    binding.icFav.setImageResource(R.drawable.ic_favorite_filled)
                    true
                } else {
                    deleteFromFavorite(id)
                    binding.icFav.setImageResource(R.drawable.ic_favorite_outline)
                    false
                }
            }
        }
    }

//    private lateinit var id : String
    private fun addToFavorite() {
        val name = binding.tvNamaproductdetail.text.toString()
        val price = binding.tvReleaseproductdetail.text.toString()
        val image = binding.tvDescriptionproductdetail.text.toString()
        favViewModel.postFav(name, price, image)
        favViewModel.dataPostFav.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), "Sukses tambah favorit", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed menambah favorit", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun deleteFromFavorite(id: Int) {
        favViewModel.deleteFav(id)
        favViewModel.deleteFav.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), "Sukses menghapus favorit", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Failed menghapus favorit", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun checkFavorite(id: Int) {
        favViewModel.isCheck(id)
       favViewModel.isFav.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it) {
                    isFavorite = true
                    binding.icFav.setImageResource(R.drawable.ic_favorite_filled)
                } else {
                    isFavorite = false
                    binding.icFav.setImageResource(R.drawable.ic_favorite_outline)
                }
            } else {
                Log.d("CHECK_FAV", "checkFavoriteMovie: $it")
            }
        }
    }
}