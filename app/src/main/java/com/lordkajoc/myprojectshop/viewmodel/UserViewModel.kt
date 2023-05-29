package com.lordkajoc.myprojectshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lordkajoc.myprojectshop.data.network.ApiService
import com.lordkajoc.myprojectshop.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val Client: ApiService): ViewModel() {

    private var livedataUser : MutableLiveData<List<DataUserPostItem>> = MutableLiveData()
    val dataPostUser: LiveData<List<DataUserPostItem>> get() = livedataUser
    fun postUserRegister(dataUsers:DataUsersResponseItem){
        //memakai callback yang retrofit
        Client.registerUser(dataUsers).enqueue(object : Callback<List<DataUserPostItem>> {
            override fun onResponse(
                call: Call<List<DataUserPostItem>>,
                response: Response<List<DataUserPostItem>>

            ) {
                if (response.isSuccessful){
                    livedataUser.postValue(response.body())
                }else{
                    livedataUser.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<List<DataUserPostItem>>, t: Throwable) {
                livedataUser.postValue(emptyList())
            }
        })
    }

    private var livedataUserLogin : MutableLiveData<List<DataUsersResponseItem>> = MutableLiveData()
    val dataLoginUser: LiveData<List<DataUsersResponseItem>> get() = livedataUserLogin
    fun UserLogin(){
        //memakai callback yang retrofit
        Client.getAllUser().enqueue(object : Callback<List<DataUsersResponseItem>> {
            override fun onResponse(
                call: Call<List<DataUsersResponseItem>>,
                response: Response<List<DataUsersResponseItem>>

            ) {
                if (response.isSuccessful){
                    livedataUserLogin.postValue(response.body())
                }else{
                    livedataUserLogin.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<List<DataUsersResponseItem>>, t: Throwable) {
                livedataUserLogin.postValue(emptyList())
            }
        })
    }
}