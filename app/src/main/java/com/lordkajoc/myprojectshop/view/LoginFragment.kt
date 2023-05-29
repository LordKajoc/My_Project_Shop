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
    lateinit var sharepref : SharedPreferences

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

        sharepref = requireContext().getSharedPreferences("LOGGED_IN" , Context.MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmaillogin.text.toString()
            val password = binding.etPasswordlogin.text.toString()
            auth(email,password)
//            if (binding.etEmaillogin.text.toString().isEmpty()){
//                binding.etEmaillogin.setError("Isi Username")
//            }else if(binding.etPasswordlogin.text.toString().isEmpty()){
//                binding.etPasswordlogin.setError("Isi Password")
//            }else{
////                forLogin()
//            }
        }

        binding.tvBelumPunyaAkun.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment2)
        }
    }

    private fun auth(email: String, password: String) {
        RetrofitClient.instance.getAllUser()
            .enqueue(object : Callback<List<DataUsersResponseItem>> {
                override fun onResponse(
                    call: Call<List<DataUsersResponseItem>>,
                    response: Response<List<DataUsersResponseItem>>,
                ) {
                    if (response.isSuccessful){
                        val resBody = response.body()
                        if (resBody != null){
                            Log.d(tag,"RESPONSE : ${resBody.toString()}")
                            for (i in 0 until resBody.size) {
                                if(resBody[i].email.equals(email) && resBody[i].password.equals(password)) {
                                    var addData = sharepref.edit()
                                    addData.putString("email", resBody[i].email)
                                    addData.putString("username",resBody[i].name)
                                    addData.putString("password",resBody[i].password)
                                    addData.putString("id",resBody[i].idUsers)
                                    addData.apply()
                                    // Clear error text
                                    //binding.etPasswordlogin.error = null
                                    //binding.etEmaillogin.error = null

                                    Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
                                    Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show()
                                } else {
                                    // Set error text
                                    binding.etPasswordlogin.error = "Password Tidak Sesuai"
                                    binding.etEmaillogin.error ="Email Tidak Sesuai"
                                    Toast.makeText(context, "Invalid Username or Password", Toast.LENGTH_SHORT).show()
                                }
                                break
                            }
                        }
                    }else{
                        Toast.makeText(context, "Gagal Load Data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<DataUsersResponseItem>>, t: Throwable) {
                    Toast.makeText(context, "Kesalahan", Toast.LENGTH_SHORT).show()
                }

            })
    }
//    lateinit var listuserlogin : List<DataUsersResponseItem>
//    private fun forLogin(){
//        userVM = ViewModelProvider(this).get(UserViewModel::class.java)
//        userVM.dataLoginUser.observe(viewLifecycleOwner, Observer {
//            listuserlogin = it
//            loginAuth(listuserlogin)
//        })
//        userVM.UserLogin()
//    }

//    private fun loginAuth(userDataList : List<DataUsersResponseItem>) {
//        //make shared preference that saving log in activity history
//        sharepref = requireActivity().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
//
//        //get all data from user input
//        val inputEmail = binding.etEmaillogin.text.toString()
//        val inputPassword = binding.etPasswordlogin.text.toString()
//
//        //checking email and password of user to authenticate
//        for(i in userDataList.indices){
//            if(inputPassword == userDataList[i].password && inputEmail == userDataList[i].email){
//                Toast.makeText(requireContext(), "Berhasil login", Toast.LENGTH_SHORT).show()
//                //bundling all information about user
//                navigationBundlingSf(userDataList[i])
//            }
//            if(i == userDataList.lastIndex && inputPassword != userDataList[i].password && inputEmail != userDataList[i].email){
//                Toast.makeText(requireContext(), "Gagal login", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

//    private fun navigationBundlingSf(currentUser: DataUsersResponseItem){
//        sharepref = requireActivity().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
//        //shared pref to save log in history
//        val sharedPref = sharepref.edit()
//        sharedPref.putString("DATAEMAIL", currentUser.email)
//        sharedPref.putString("DATAPASSWORD", currentUser.password)
//        sharedPref.putString("DATANAMA", currentUser.name)
//        sharedPref.putString("DATAIMAGE", currentUser.image)
//        sharedPref.putString("DATAID", currentUser.idUsers)
//        sharedPref.apply()
//        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//
//        //some code to fix destination error
//        /*error : java.lang.IllegalArgumentException: Navigation action/destination
//        a cannot be found from the current destination Destination(id/loginFragment) label=Home
//        class=com.sample.store.main.dashboard.ui.ui.home.mainui.HomeFragment*/
////        val currentDestinationIsLogin = this.findNavController().currentDestination == this.findNavController().findDestination(R.id.loginFragment)
////        val currentDestinationIsMainHomeFragment = this.findNavController().currentDestination == this.findNavController().findDestination(R.id.homeFragment)
////
////        if(currentDestinationIsLogin && !currentDestinationIsMainHomeFragment){
////            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
////        }
//
//
//    }
}