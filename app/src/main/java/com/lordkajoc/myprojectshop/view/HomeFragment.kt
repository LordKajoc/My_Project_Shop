package com.lordkajoc.myprojectshop.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lordkajoc.myprojectshop.R
import com.lordkajoc.myprojectshop.databinding.FragmentHomeBinding
import com.lordkajoc.myprojectshop.view.adapter.NewsAdapter
import com.lordkajoc.myprojectshop.view.adapter.ProductAdapter
import com.lordkajoc.myprojectshop.view.adapter.SlidersAdapter
import com.lordkajoc.myprojectshop.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModelHomeVm: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSlider()
        getNews()
        getProduct()
        binding.tocart.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)
        }
    }
    fun getSlider(){
        viewModelHomeVm = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModelHomeVm.getSlider()
        viewModelHomeVm.dataSlider.observe(viewLifecycleOwner, Observer{
            binding.viewPager.adapter = SlidersAdapter(it)
        })
    }

    fun getNews(){
        viewModelHomeVm = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModelHomeVm.getListNews()
        viewModelHomeVm.dataNews.observe(viewLifecycleOwner, Observer{
            binding.rvListnewshome.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            if (it!= null) {
                binding.rvListnewshome.adapter = NewsAdapter(it)
            }
        })
    }

    fun getProduct(){
        viewModelHomeVm = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModelHomeVm.getListProduct()
        viewModelHomeVm.dataProduct.observe(viewLifecycleOwner, Observer{
            binding.rvListproducthome.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            if (it!= null) {
                binding.rvListproducthome.adapter = ProductAdapter(it)
            }
        })
    }


//    @SuppressLint("NotifyDataSetChanged")
//    fun getNews(){
//        viewModelHomeVm = ViewModelProvider(this).get(HomeVm::class.java)
//        viewModelHomeVm.getDataNews()
//        viewModelHomeVm.liveDataNews.observe(viewLifecycleOwner, Observer{
//            binding.rvconNews.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            binding.rvconNews.adapter = NewsAdapter(it)
//        })
//    }

}