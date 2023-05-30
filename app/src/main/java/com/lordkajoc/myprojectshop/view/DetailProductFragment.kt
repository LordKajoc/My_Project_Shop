package com.lordkajoc.myprojectshop.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.lordkajoc.myprojectshop.R
import com.lordkajoc.myprojectshop.databinding.FragmentDetailProductBinding
import com.lordkajoc.myprojectshop.model.*
import com.lordkajoc.myprojectshop.viewmodel.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailProductFragment : Fragment() {

    private lateinit var binding: FragmentDetailProductBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favViewModel: FavoriteViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var profileViewModel : ProfileViewModel
    private lateinit var idUser :String
    private lateinit var idProduct :String
    private lateinit var idFav: String

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var selectedCart : DataCartResponseItem
    private lateinit var selectedProduct: DataFavProductResponseItem
    private lateinit var dataFav :DataFavProductResponseItem
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
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        var getData = arguments?.getSerializable("ID") as DataProductResponseItem
        idProduct = getData.idProduct
        binding.tvDetail.text = idProduct
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
        viewModel.detailProduct.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    binding.tvNamaproductdetail.text = it.name.toString()
                    binding.tvReleaseproductdetail.text = "Release: " + it.createdAt.toString()
                    Glide.with(requireContext())
                        .load("${it.productImage}")
                        .into(binding.ivProductimagedetail)

                    binding.tvDescriptionproductdetail.text = """Description:
                        |
                    """.trimMargin() + it.description.toString()

                    sharedPreferences = requireContext().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
                    idUser = sharedPreferences.getString("id","").toString()

                    //if (idUser.isNotEmpty()) { // Cek apakah pengguna sudah login
                        selectedProduct = DataFavProductResponseItem(
                            it.createdAt!!,
                            it.description!!,
                            it.idProduct!!,
                            it.name!!,
                            it.price!!,
                            it.productImage!!,
                            idUser
                        )
                        getPostCart(idUser, it)
                        checkFavorite(selectedProduct.idFav)
                        setFavoriteListener(selectedProduct.userId, selectedProduct.idFav, it)
                        selectedCart = DataCartResponseItem(
                            it.createdAt!!,
                            it.description!!,
                            it.idProduct!!,
                            it.name!!,
                            it.price!!,
                            it.productImage!!,
                            idUser
                        )
                    //} else {
                        // Tambahkan kode untuk menampilkan pesan bahwa pengguna harus login atau memiliki ID pengguna untuk menambahkan ke keranjang
//                        Toast.makeText(requireContext(), "Anda harus login atau memiliki ID pengguna untuk menambahkan ke keranjang", Toast.LENGTH_SHORT)
//                            .show()
                   // }
                }
            }
        }
    }


    private fun setFavoriteListener(idUser: String, idProduct:String,fav : DataDetailProductItem) {
        binding.icFav.apply {
//            if (idUser.isNotEmpty()) { // Cek apakah pengguna sudah login
            setOnClickListener {
                isFavorite =
                    if (!isFavorite) {
                        addToFavorite(idUser, fav)
                        binding.icFav.setImageResource(R.drawable.ic_favorite_filled)
                        true
                    } else {
                        deleteFromFavorite(idProduct)
                        binding.icFav.setImageResource(R.drawable.ic_favorite_outline)
                        false
                    }
            }
//            }else{
                // Tambahkan kode untuk menampilkan pesan bahwa pengguna harus login atau memiliki ID pengguna untuk menambahkan ke keranjang
                        //Toast.makeText(requireContext(), "Anda harus login untuk menambahkan ke favorite", Toast.LENGTH_SHORT).show()
//            }
        }
    }

    //    private lateinit var id : String
    private fun addToFavorite(userId:String,fav: DataDetailProductItem) {
        if (idUser.isNotEmpty()) { // Cek apakah pengguna sudah login
            favViewModel.postFav(userId, fav)
            favViewModel.dataPostFav.observe(viewLifecycleOwner) {
                if (it != null) {
                    Toast.makeText(requireContext(), "Sukses tambah favorit", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(requireContext(), "Failed menambah favorit", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }else{
        // Tambahkan kode untuk menampilkan pesan bahwa pengguna harus login atau memiliki ID pengguna untuk menambahkan ke keranjang
        Toast.makeText(requireContext(), "Anda harus login untuk menambahkan ke Favorite", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_detailProductFragment_to_loginFragment)
        }
    }

    private fun deleteFromFavorite(favId:String) {
        favViewModel.deleteFav(favId)
        favViewModel.dataDeleteFav.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), "Sukses menghapus favorit", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Gagal menghapus favorit", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun checkFavorite(favId: String) {
        favViewModel.checkFav(favId)
        favViewModel.dataCheckFav.observe(viewLifecycleOwner) {
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
    private fun addToCart(id: String,cart: DataDetailProductItem){
        if (idUser.isNotEmpty()) { // Cek apakah pengguna sudah login
            cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        cartViewModel.postCart(id, cart)
//        cartViewModel.postCart(cart = DataDetailProductItem("","","","","","",""))
        cartViewModel.dataCart.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), "Sukses tambah Cart", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed menambah Cart", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        }else{
            // Tambahkan kode untuk menampilkan pesan bahwa pengguna harus login atau memiliki ID pengguna untuk menambahkan ke keranjang
            Toast.makeText(requireContext(), "Anda harus login untuk menambahkan ke keranjang", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_detailProductFragment_to_loginFragment)
        }
    }
    private fun getPostCart(id:String, cart:DataDetailProductItem){
        binding.icCart.apply {
            setOnClickListener {
                addToCart(id,cart)
//                findNavController().navigate(R.id.action_detailProductFragment_to_cartFragment)
            }
        }
    }
}