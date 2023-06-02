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
import com.lordkajoc.myprojectshop.databinding.FragmentHistoryBinding
import com.lordkajoc.myprojectshop.view.adapter.TransHistoryAdapter
import com.lordkajoc.myprojectshop.viewmodel.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class HistoryFragment : Fragment() {
    lateinit var binding: FragmentHistoryBinding
    private lateinit var vmCart : HistoryViewModel
    private lateinit var idUser : String
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
        idUser = sharedPreferences.getString("id","").toString()
        if (sharedPreferences.getString("id", "")!!.isEmpty()) {
            binding.rvTrans.visibility = View.GONE
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Login")
                .setMessage("Anda Belum Login")
                .setCancelable(false)
                .setNegativeButton("Cancel") { dialog, which ->
                    // Respond to negative button press
                    findNavController().navigate(R.id.action_historyFragment_to_homeFragment)
                }
                .setPositiveButton("Login") { dialog, which ->
                    // Respond to positive button press
                    findNavController().navigate(R.id.action_historyFragment_to_loginFragment)
                }
                .show()
        } else if (sharedPreferences.getString("id", "")!!.isNotEmpty()) {
            getDataTrans(idUser)
        }
    }

    fun getDataTrans(userId:String){
        vmCart = ViewModelProvider(this).get(HistoryViewModel::class.java)
        vmCart.getCart(userId)
        vmCart.dataListTransHistory.observe(viewLifecycleOwner, Observer{
            binding.rvTrans.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false)
            binding.rvTrans.adapter = TransHistoryAdapter(it)
        })
    }

    override fun onStart() {
        super.onStart()
        getDataTrans(idUser)
    }
}