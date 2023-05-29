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

    val dataPostFav: LiveData<List<DataFav>> get() = livedataFav
    val deleteFav: LiveData<List<DataFav>> get() = livedataFav
    private val _IsFav: MutableLiveData<Boolean> = MutableLiveData()
    val isFav: LiveData<Boolean> get() = _IsFav

    fun postFav( name:String, price:String, image:String){
        //memakai callback yang retrofit
        Client.getPostFavorite(DataFav(name,price,image)).enqueue(object :
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





}