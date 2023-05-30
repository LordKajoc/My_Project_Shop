package com.lordkajoc.myprojectshop.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.lordkajoc.myprojectshop.R
import com.lordkajoc.myprojectshop.databinding.FragmentProfileBinding
import com.lordkajoc.myprojectshop.model.DataUsersResponseItem
import com.lordkajoc.myprojectshop.viewmodel.ProfileViewModel
import com.lordkajoc.myprojectshop.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    lateinit var listuser: List<DataUsersResponseItem>
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var model : ProfileViewModel
    private lateinit var oldPassword : String
    private lateinit var id : String
    //lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider(this).get(ProfileViewModel::class.java)
        sharedPreferences = requireContext().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("username","username")
//        editor = share.edit()
        id = sharedPreferences.getString("id", "").toString()

        getDataProfile()
//        binding.btnLogout.setOnClickListener {
//            firebaseAuth = FirebaseAuth.getInstance()
//            firebaseAuth.signOut()
//            val addUser = sharedPreferences.edit()
//            addUser.remove("nama")
//            addUser.remove("tgl")
//            addUser.remove("alamat")
//            addUser.apply()
//            Toast.makeText(context, "Keluar Berhasil", Toast.LENGTH_SHORT).show()
//            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
//        }
    }

    fun getDataProfile() {
        model.getProfileById(id)
        model.dataUserProfile.observe(viewLifecycleOwner){
            if (it != null){
                binding.etUsernameprofile.setText(it.name)
                binding.etAddressprofile.setText(it.email)
                binding.etTgllahirprofile.setText(it.password)
                oldPassword = it.password
            }
        }
    }
}

//    fun initData(userdatalist : List<DataUsersResponseItem>){
//
//        for (i in userdatalist.indices){
//            binding.etUsernameprofile.setText(userdatalist[i].name)
////            cnameProfile.setText(userdatalist[i].completeName)
////            addressProfile.setText(userdatalist[i].address)
////            birthdateProfile.setText(userdatalist[i].dateofbirth)
//        }