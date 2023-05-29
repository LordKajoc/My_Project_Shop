package com.lordkajoc.myprojectshop.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.lordkajoc.myprojectshop.R
import com.lordkajoc.myprojectshop.databinding.FragmentRegisterBinding
import com.lordkajoc.myprojectshop.model.DataUsersResponseItem
import com.lordkajoc.myprojectshop.viewmodel.HomeViewModel
import com.lordkajoc.myprojectshop.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var userVM: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userVM = ViewModelProvider(this).get(UserViewModel::class.java)
        binding.btnDaftar.setOnClickListener {
            register()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun register() {
        val username = binding.etUsernameregister.text.toString()
        val email = binding.etEmailregister.text.toString()
        val password = binding.etPasswordregister.text.toString()
        val passwordConfirm = binding.etRepasswordregister.text.toString()
        val currentDateTime: LocalDateTime = LocalDateTime.now()

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all the field", Toast.LENGTH_SHORT).show()
        } else {
            if (password == passwordConfirm) {
                userVM.postUserRegister(dataUsers = DataUsersResponseItem("$currentDateTime",email,"","",username,password))
                Toast.makeText(requireContext(), "Registration Success", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            } else {
                Toast.makeText(requireContext(), "Password not match", Toast.LENGTH_SHORT).show()
            }
        }
    }
}