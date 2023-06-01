package com.lordkajoc.myprojectshop.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lordkajoc.myprojectshop.databinding.FragmentCartBinding
import com.lordkajoc.myprojectshop.model.DataCartResponseItem
import com.lordkajoc.myprojectshop.view.adapter.CartAdapter
import com.lordkajoc.myprojectshop.view.adapter.ProductAdapter
import com.lordkajoc.myprojectshop.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding
    private lateinit var vmCart : CartViewModel
    private lateinit var idUser : String
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var idCart: String
    private val cartViewModel: CartViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
        idUser = sharedPreferences.getString("id","").toString()
        getDataCart(idUser)
        //deleteCart(idUser, idCart)



    }



    fun getDataCart(userId:String){
        vmCart = ViewModelProvider(this).get(CartViewModel::class.java)
        vmCart.getCart(userId)
        vmCart.dataCart.observe(viewLifecycleOwner, Observer{
            binding.rvCart.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false)
            binding.rvCart.adapter = CartAdapter(it, cartViewModel)
        })
    }

    override fun onStart() {
        super.onStart()
        getDataCart(idUser)
    }

//    private fun deleteCart(IdUser:String, idCart:String) {
//        vmCart.deleteCart(idUser, idCart)
//        vmCart.dataCart.observe(viewLifecycleOwner) {
//            if (it != null) {
//                binding.rvCart.adapter = CartAdapter(it)
//                Toast.makeText(requireContext(), "Sukses menghapus Cart", Toast.LENGTH_SHORT)
//                    .show()
//            } else {
//                Toast.makeText(requireContext(), "Gagal menghapus Cart", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//    }




}