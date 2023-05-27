package com.lordkajoc.myprojectshop.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.lordkajoc.myprojectshop.databinding.FragmentDetailProductBinding
import com.lordkajoc.myprojectshop.model.DataNewsResponseItem
import com.lordkajoc.myprojectshop.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailProductFragment : Fragment() {

    private lateinit var binding: FragmentDetailProductBinding
    private lateinit var viewModel: HomeViewModel
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
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        val id = arguments?.getInt("ID")
        if (id != null) {
            viewModel.getProductById(id)
            observeDetailProduct()
//            setFavoriteListener()
//            checkFavoriteMovie(id)
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
//                    selectedMovie = FavoriteMovie(
//                        it.id!!,
//                        it.title!!,
//                        it.releaseDate!!,
//                        it.posterPath!!
//                    )
                }
            }
        }
    }

//    private fun setFavoriteListener() {
//        binding.icFav.apply {
//            setOnClickListener {
//                isFavorite = if (!isFavorite) {
//                    addToFavorite(selectedMovie)
//                    binding.icFav.setImageResource(R.drawable.ic_fav)
//                    true
//                } else {
//                    deleteFromFavorite(selectedMovie)
//                    binding.icFav.setImageResource(R.drawable.ic_not_fav)
//                    false
//                }
//            }
//        }
//    }
//
//    private fun addToFavorite(movie: FavoriteMovie) {
//        viewModel.addFavMovie(movie)
//        viewModel.favMovie.observe(viewLifecycleOwner) {
//            if (it != null) {
//                Toast.makeText(requireContext(), "Sukses tambah favorit", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(requireContext(), "Failed menambah favorit", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//    }
//
//    private fun deleteFromFavorite(movie: FavoriteMovie) {
//        viewModel.deleteFavMovie(movie)
//        viewModel.deleteFavMovie.observe(viewLifecycleOwner) {
//            if (it != null) {
//                Toast.makeText(requireContext(), "Sukses menghapus favorit", Toast.LENGTH_SHORT)
//                    .show()
//            } else {
//                Toast.makeText(requireContext(), "Failed menghapus favorit", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//    }
//
//
//    private fun checkFavoriteMovie(movieId: Int) {
//        viewModel.isFavoriteMovie(movieId)
//        viewModel.isFav.observe(viewLifecycleOwner) {
//            if (it != null) {
//                if (it) {
//                    isFavorite = true
//                    binding.icFav.setImageResource(R.drawable.ic_fav)
//                } else {
//                    isFavorite = false
//                    binding.icFav.setImageResource(R.drawable.ic_not_fav)
//                }
//            } else {
//                Log.d("CHECK_FAV", "checkFavoriteMovie: $it")
//            }
//        }
//    }
}