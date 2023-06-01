package com.lordkajoc.myprojectshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lordkajoc.myprojectshop.data.network.ApiService
import com.lordkajoc.myprojectshop.model.DataDetailNewsItem
import com.lordkajoc.myprojectshop.model.DataUserPostItem
import com.lordkajoc.myprojectshop.model.DataUsers
import com.lordkajoc.myprojectshop.model.DataUsersResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val Client: ApiService) : ViewModel() {

//    private var livedataUserProfile: MutableLiveData<List<DataUsersResponseItem>> =
//        MutableLiveData()
//    val dataUserProfile: LiveData<List<DataUsersResponseItem>> get() = livedataUserProfile
//    fun getProfileUser(id: String) {
//        //memakai callback yang retrofit
//        Client.getProfileUser(id).enqueue(object :
//            Callback<List<DataUsersResponseItem>> {
//            override fun onResponse(
//                call: Call<List<DataUsersResponseItem>>,
//                response: Response<List<DataUsersResponseItem>>
//
//            ) {
//                if (response.isSuccessful) {
//                    livedataUserProfile.postValue(response.body())
//                } else {
//                    livedataUserProfile.postValue(emptyList())
//                }
//            }
//
//            override fun onFailure(call: Call<List<DataUsersResponseItem>>, t: Throwable) {
//                livedataUserProfile.postValue(emptyList())
//            }
//        })
//    }

    private val livedataUserProfile: MutableLiveData<DataUsersResponseItem?> = MutableLiveData()
    val dataUserProfile: LiveData<DataUsersResponseItem?> get() = livedataUserProfile

    fun getProfileById(id: String) {
        Client.getProfileUser(id).enqueue(object : Callback<DataUsersResponseItem> {
            override fun onResponse(
                call: Call<DataUsersResponseItem>,
                response: Response<DataUsersResponseItem>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        livedataUserProfile.postValue(responseBody)
                    }
                }
            }

            override fun onFailure(call: Call<DataUsersResponseItem>, t: Throwable) {

            }
        })
    }

    private var liveDataUpdateUser: MutableLiveData<DataUsersResponseItem> = MutableLiveData()
    val dataUpdateUser: LiveData<DataUsersResponseItem> get() = liveDataUpdateUser

    fun updateUser(iduser:String, updateUser:DataUsersResponseItem) {

        Client.putUpdateUser(iduser, updateUser)
            .enqueue(object : Callback<DataUsersResponseItem> {
                override fun onResponse(
                    call: Call<DataUsersResponseItem>,
                    response: Response<DataUsersResponseItem>
                ) {
                    if (response.isSuccessful) {
                        liveDataUpdateUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DataUsersResponseItem>, t: Throwable) {
                }
            })
    }
}