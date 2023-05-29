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

class FavoriteViewModel @Inject constructor(private val Client: ApiService) : ViewModel()  {
    private val _IsFav: MutableLiveData<Boolean> = MutableLiveData()
    val isFav: LiveData<Boolean> get() = _IsFav

    private val liveDataPostFav: MutableLiveData<List<DataFavProductResponseItem>> = MutableLiveData()
    val dataPostFav: LiveData<List<DataFavProductResponseItem>> get() = liveDataPostFav

    fun postFav(userId:String, dataFav : DataFavProductResponseItem){
        //memakai callback yang retrofit
        Client.getPostFavorite(userId,dataFav).enqueue(object : Callback<List<DataFavProductResponseItem>> {
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


    private val liveDataDeleteFav: MutableLiveData<String?> = MutableLiveData()
    val dataDeleteFav: LiveData<String?> get() = liveDataDeleteFav
    fun deleteFav(userId: String,idProduct:String){
        //memakai callback yang retrofit
        Client.getDeleteFavorite(userId,idProduct).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    liveDataDeleteFav.postValue(response.body())
                }else{
                    liveDataDeleteFav.postValue(null)
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                liveDataDeleteFav.postValue(null)
            }
        })
    }

    fun isCheck(id: String){
        //memakai callback yang retrofit
        Client.check(id).enqueue(object :
            Callback<Boolean> {
            override fun onResponse(
                call: Call<Boolean>,
                response: Response<Boolean>

            ) {
                if (response.isSuccessful){
                    _IsFav.postValue(response.body())
                }else{
                    _IsFav.postValue(false)
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                _IsFav.postValue(false)
            }
        })
    }





}