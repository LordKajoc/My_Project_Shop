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

class FavoriteViewModel @Inject constructor(private val Client: ApiService) : ViewModel() {

    private val liveDataPostFav: MutableLiveData<List<DataFavProductResponseItem>> =
        MutableLiveData()
    val dataPostFav: LiveData<List<DataFavProductResponseItem>> get() = liveDataPostFav

    fun postFav(userId: String, dataFav: DataDetailProductItem) {
        //memakai callback yang retrofit
        Client.getPostFavorite(userId, dataFav)
            .enqueue(object : Callback<List<DataFavProductResponseItem>> {
                override fun onResponse(
                    call: Call<List<DataFavProductResponseItem>>,
                    response: Response<List<DataFavProductResponseItem>>

                ) {
                    if (response.isSuccessful) {
                        liveDataPostFav.postValue(response.body())
                    } else {
                        liveDataPostFav.postValue(emptyList())
                    }
                }

                override fun onFailure(call: Call<List<DataFavProductResponseItem>>, t: Throwable) {
                    liveDataPostFav.postValue(emptyList())
                }
            })
    }

    private val liveDataFav: MutableLiveData<List<DataFavProductResponseItem>> = MutableLiveData()
    val dataFav: LiveData<List<DataFavProductResponseItem>> get() = liveDataFav

    fun getFav(userId: String) {
        //memakai callback yang retrofit
        Client.getFavorite(userId).enqueue(object : Callback<List<DataFavProductResponseItem>> {
            override fun onResponse(
                call: Call<List<DataFavProductResponseItem>>,
                response: Response<List<DataFavProductResponseItem>>

            ) {
                if (response.isSuccessful) {
                    liveDataFav.postValue(response.body())
                } else {
                    liveDataFav.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<List<DataFavProductResponseItem>>, t: Throwable) {
                liveDataFav.postValue(emptyList())
            }
        })
    }

    private val liveDataDeleteFav: MutableLiveData<Boolean> =
        MutableLiveData()
    val dataDeleteFav: LiveData<Boolean> get() = liveDataDeleteFav
    fun deleteFav(userId: String ) {
        //memakai callback yang retrofit
        Client.getDeleteFavorite(userId)
            .enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    liveDataDeleteFav.postValue(true)
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    liveDataDeleteFav.postValue(false)
                }
            })
    }

    private val liveDataCheckFav: MutableLiveData<Boolean> = MutableLiveData()
    val dataCheckFav: LiveData<Boolean> get() = liveDataCheckFav
    fun checkFav(userId: String,) {
        //memakai callback yang retrofit
        Client.checkFav(userId).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    liveDataCheckFav.postValue(response.body())
                } else {
                    liveDataCheckFav.postValue(false)
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                liveDataCheckFav.postValue(false)
            }
        })
    }
    fun getFav(){
        //memakai callback yang retrofit
        Client.getFav().enqueue(object : Callback<List<DataFavProductResponseItem>> {
            override fun onResponse(
                call: Call<List<DataFavProductResponseItem>>,
                response: Response<List<DataFavProductResponseItem>>

            ) {
                if (response.isSuccessful){
                    liveDataPostFav.postValue(response.body())
                }else{
                    liveDataPostFav.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<List<DataFavProductResponseItem>>, t: Throwable) {
                liveDataPostFav.postValue(emptyList())
            }
        })
    }



}