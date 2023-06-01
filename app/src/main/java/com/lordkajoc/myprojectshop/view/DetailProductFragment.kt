package com.lordkajoc.myprojectshop.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.lordkajoc.myprojectshop.R
import com.lordkajoc.myprojectshop.databinding.FragmentDetailProductBinding
import com.lordkajoc.myprojectshop.model.*
import com.lordkajoc.myprojectshop.viewmodel.CartViewModel
import com.lordkajoc.myprojectshop.viewmodel.FavoriteViewModel
import com.lordkajoc.myprojectshop.viewmodel.HomeViewModel
import com.lordkajoc.myprojectshop.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProductFragment : Fragment() {

    private lateinit var binding: FragmentDetailProductBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favViewModel: FavoriteViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var idProduct: String
    private var idFav: String? = null

    private lateinit var sharedPreferences: SharedPreferences
    private var isFavorite = false
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
        sharedPreferences =
            requireContext().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
        val getiiuser = sharedPreferences.getString("id", "").toString()
        favViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        var getData = arguments?.getSerializable("ID") as DataProductResponseItem
        idProduct = getData.idProduct
        binding.tvDetail.text = getiiuser
        if (idProduct != null) {
            viewModel.getProductById(idProduct)
            observeDetailProduct()
            //test crashlytics
            binding.btnCrashdetail.setOnClickListener {
                throw RuntimeException("Test Crash") // Force a crash
            }
        }
    }

    private fun observeDetailProduct() {
        viewModel.detailProduct.observe(viewLifecycleOwner) { detailproduct ->
            binding.apply {
                if (detailproduct != null) {
                    binding.tvNamaproductdetail.text = detailproduct.name
                    binding.tvReleaseproductdetail.text = "Release: " + detailproduct.createdAt
                    Glide.with(requireContext())
                        .load("${detailproduct.productImage}")
                        .into(binding.ivProductimagedetail)
                    //        binding.tvSinopsisfilmdetail.text = """Overview:
//            ${getfilm.overview}
//        """.trimIndent()
                    binding.tvDescriptionproductdetail.text = """Description:
                        |
                    """.trimMargin() + detailproduct.description.toString()
                }
                //check is prodcut in favorite
                sharedPreferences =
                    requireContext().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
                val idUserforDetail = sharedPreferences.getString("id", "").toString()
                favViewModel.getFav(idUserforDetail)
                favViewModel.dataFav.observe(viewLifecycleOwner) { favproduct ->
                    for (i in favproduct.indices) {
                        if (favproduct[i].name == detailproduct.name) {
                            isFavorite = true
                            binding.icFav.setImageResource(R.drawable.ic_favorite_filled)
                            idFav = favproduct[i].idFav
                            break
                        } else {
                            isFavorite = false
                            binding.icFav.setImageResource(R.drawable.ic_favorite_outline)
                        }
                    }
                }
                setFavoriteListener()
                getPostCart()
            }
        }
    }

    private fun setFavoriteListener() {
        binding.icFav.apply {
            setOnClickListener {
                isFavorite = if (!isFavorite) {
                    addItemfavorite()
                    binding.icFav.setImageResource(R.drawable.ic_favorite_filled)
                    true
                } else {
                    deleteFromFavorite()
                    binding.icFav.setImageResource(R.drawable.ic_favorite_outline)
                    false
                }
            }
        }
    }

    private fun addToFavorite(
        id: String,
        name: String,
        productImage: String,
        price: Int,
        desc: String
    ) {
        favViewModel.postFavouriteProducts(id, name, productImage, price, desc)
        favViewModel.getProductFavorite.observe(requireActivity()) {
            if (it != null) {
                Toast.makeText(
                    requireContext(),
                    "Berhasil menambahkan ke favorit",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun addItemfavorite() {
        sharedPreferences =
            requireContext().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
        val idUser = sharedPreferences.getString("id", "").toString()
        viewModel.detailProduct.observe(viewLifecycleOwner) {
            val name = it.name
            val productImage = it.productImage
            val price = it.price.toInt()
            val desc = it.description
            val id = idUser
            addToFavorite(id, name, productImage, price, desc)
            binding.icFav.setImageResource(R.drawable.ic_favorite_filled)
        }
    }

    private fun deleteFromFavorite() {
        sharedPreferences =
            requireContext().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
        val idUser = sharedPreferences.getString("id", "").toString()
        favViewModel.deleteFavProducts(idUser, idFav!!)
        favViewModel.delProductFavorite.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), "Sukses menghapus favorit", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Gagal menghapus favorit", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun addToCart(
        id: String,
        name: String,
        productImage: String,
        price: Int,
        desc: String
    ) {
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        cartViewModel.postCart(id, name, productImage, price, desc)
    }

    private fun addItemCart() {
        sharedPreferences =
            requireContext().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
        val idUser = sharedPreferences.getString("id", "").toString()
        viewModel.detailProduct.observe(viewLifecycleOwner) {
            val name = it.name
            val productImage = it.productImage
            val price = it.price.toInt()
            val desc = it.description
            val id = idUser
            addToCart(id, name, productImage, price, desc)
            if (it != null) {
                Toast.makeText(requireContext(), "Berhasil menambahkan ke cart", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Gagal menambahkan ke cart", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun getPostCart() {
        binding.icCart.apply {
            setOnClickListener {
                addItemCart()
//                findNavController().navigate(R.id.action_detailProductFragment_to_cartFragment)
            }
        }
    }
}

