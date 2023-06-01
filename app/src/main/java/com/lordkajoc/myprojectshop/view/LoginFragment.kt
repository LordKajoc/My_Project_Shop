package com.lordkajoc.myprojectshop.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.lordkajoc.myprojectshop.R
import com.lordkajoc.myprojectshop.data.network.ApiService
import com.lordkajoc.myprojectshop.data.network.RetrofitClient
import com.lordkajoc.myprojectshop.databinding.FragmentLoginBinding
import com.lordkajoc.myprojectshop.databinding.FragmentRegisterBinding
import com.lordkajoc.myprojectshop.model.DataUsersResponseItem
import com.lordkajoc.myprojectshop.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var userVM: UserViewModel
    lateinit var sharepref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userVM = ViewModelProvider(this).get(UserViewModel::class.java)

        sharepref = requireContext().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {
            if (binding.etEmaillogin.text.toString().isEmpty()) {
                binding.etEmaillogin.setError("Isi Username")
            } else if (binding.etPasswordlogin.text.toString().isEmpty()) {
                binding.etPasswordlogin.setError("Isi Password")
            } else {
                forLogin()
            }
        }

        binding.tvBelumPunyaAkun.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment2)
        }
    }

    lateinit var listuserlogin: List<DataUsersResponseItem>
    private fun forLogin() {
        userVM = ViewModelProvider(this).get(UserViewModel::class.java)
        userVM.dataLoginUser.observe(viewLifecycleOwner, Observer {
            listuserlogin = it
            loginAuth(listuserlogin)
        })
        userVM.UserLogin()
    }

    private fun loginAuth(userDataList: List<DataUsersResponseItem>) {
        //make shared preference that saving log in activity history
        sharepref = requireActivity().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)

        //get all data from user input
        val inputEmail = binding.etEmaillogin.text.toString()
        val inputPassword = binding.etPasswordlogin.text.toString()

        //checking email and password of user to authenticate
        for (i in userDataList.indices) {
            if (inputPassword == userDataList[i].password && inputEmail == userDataList[i].email) {
                Toast.makeText(requireContext(), "Berhasil login", Toast.LENGTH_SHORT).show()
                //bundling all information about user
                navigationBundlingSf(userDataList[i])
                break
            } else if (i == userDataList.lastIndex && inputPassword != userDataList[i].password && inputEmail != userDataList[i].email) {
                binding.etPasswordlogin.error = "Password Tidak Sesuai"
                binding.etEmaillogin.error = "Email Tidak Sesuai"
            }
        }
    }

    private fun navigationBundlingSf(currentUser: DataUsersResponseItem) {
        sharepref = requireActivity().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
        //shared pref to save log in history
        val sharedPref = sharepref.edit()
        sharedPref.putString("createat", currentUser.createdAt)
        sharedPref.putString("email", currentUser.email)
        sharedPref.putString("password", currentUser.password)
        sharedPref.putString("username", currentUser.name)
        sharedPref.putString("image", currentUser.image)
        sharedPref.putString("id", currentUser.idUsers)
        sharedPref.apply()
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }
}