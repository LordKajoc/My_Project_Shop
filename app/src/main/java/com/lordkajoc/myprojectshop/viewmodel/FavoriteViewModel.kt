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
    private var livedataFav : MutableLiveData<List<DataFav>> = MutableLiveData()
    private var livedataPostFav : MutableLiveData<DataFav> = MutableLiveData()
    private var livedataDeleteFav : MutableLiveData<DataFav> = MutableLiveData()
    val dataPostFav: LiveData<DataFav> get() = livedataPostFav
    val deleteFav: LiveData<DataFav> get() = livedataDeleteFav
    private val _IsFav: MutableLiveData<Boolean> = MutableLiveData()
    val isFav: LiveData<Boolean> get() = _IsFav

    fun postFav(fav: DataFav){
        //memakai callback yang retrofit
        Client.getPostFavorite(fav).enqueue(object :
            Callback<List<DataFav>> {
            override fun onResponse(
                call: Call<List<DataFav>>,
                response: Response<List<DataFav>>

            ) {
                if (response.isSuccessful){
                    livedataFav.postValue(response.body())
                }else{
                    livedataFav.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<List<DataFav>>, t: Throwable) {
                livedataFav.postValue(emptyList())
            }
        })
    }

    fun deleteFav(id: Int){
        //memakai callback yang retrofit
        Client.getDeleteFav(id.toString()).enqueue(object :
            Callback<List<DataFav>> {
            override fun onResponse(
                call: Call<List<DataFav>>,
                response: Response<List<DataFav>>

            ) {
                if (response.isSuccessful){
                    livedataFav.postValue(response.body())
                }else{
                    livedataFav.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<List<DataFav>>, t: Throwable) {
                livedataFav.postValue(emptyList())
            }
        })
    }

    fun isCheck(id: Int){
        //memakai callback yang retrofit
        Client.check(id.toString()).enqueue(object :
            Callback<Boolean> {
            override fun onResponse(
                call: Call<Boolean>,
                response: Response<Boolean>

            ) {
                if (response.isSuccessful){
                    _IsFav.postValue(response.body())
                }else{
                    livedataFav.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                livedataFav.postValue(emptyList())
            }
        })
    }





}