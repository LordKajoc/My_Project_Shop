package com.lordkajoc.myprojectshop.viewmodel

import android.util.Log
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


//    private val liveDataPostFav: MutableLiveData<List<DataFavProductResponseItem>> =
//        MutableLiveData()
//    val dataPostFav: LiveData<List<DataFavProductResponseItem>> get() = liveDataPostFav

//    fun postFav(userId: String, dataFav: DataDetailProductItem) {
//        //memakai callback yang retrofit
//        Client.getPostFavorite(userId, dataFav)
//            .enqueue(object : Callback<List<DataFavProductResponseItem>> {
//                override fun onResponse(
//                    call: Call<List<DataFavProductResponseItem>>,
//                    response: Response<List<DataFavProductResponseItem>>
//
//                ) {
//                    if (response.isSuccessful) {
//                        liveDataPostFav.postValue(response.body())
//                    } else {
//                        liveDataPostFav.postValue(emptyList())
//                    }
//                }
//
//                override fun onFailure(call: Call<List<DataFavProductResponseItem>>, t: Throwable) {
//                    liveDataPostFav.postValue(emptyList())
//                }
//            })
//    }

    private val liveLoadData = MutableLiveData<Boolean>()
    val loadData: LiveData<Boolean> = liveLoadData

    // get item favourite
    private val getItemProductFavourite = MutableLiveData<DataFavProductResponseItem>()
    val getProductFavorite: LiveData<DataFavProductResponseItem> = getItemProductFavourite

    fun postFavouriteProducts(
        id: String,
        name: String,
        productImage: String,
        price: Int,
        description: String
    ) {
        liveLoadData.value = true
        Client.postFavouriteProduct(id, name, productImage, price, description)
            .enqueue(object : Callback<DataFavProductResponseItem> {
                override fun onResponse(
                    call: Call<DataFavProductResponseItem>,
                    response: Response<DataFavProductResponseItem>
                ) {
                    if (response.isSuccessful) {
                        liveLoadData.value = false
                        getItemProductFavourite.value = response.body()

                    } else {
                        liveLoadData.value = false
                    }
                }

                override fun onFailure(call: Call<DataFavProductResponseItem>, t: Throwable) {
                    liveLoadData.value = false
                }
            })
    }

    // get item favourite
    private val delItemProductFavourite = MutableLiveData<DataFavProductResponseItem>()
    val delProductFavorite: LiveData<DataFavProductResponseItem> = delItemProductFavourite
    fun deleteFavProducts(userId: String, favId: String) {
        liveLoadData.value = true
        Client.deleteFavouriteProduct(userId, favId)
            .enqueue(object : Callback<DataFavProductResponseItem> {
                override fun onResponse(
                    call: Call<DataFavProductResponseItem>,
                    response: Response<DataFavProductResponseItem>
                ) {
                    if (response.isSuccessful) {
                        liveLoadData.value = false
                        delItemProductFavourite.value = response.body()

                    } else {
                        liveLoadData.value = false
                    }
                }

                override fun onFailure(call: Call<DataFavProductResponseItem>, t: Throwable) {
                    liveLoadData.value = false
                }
            })
    }

    // check item favourite
    private val checkItemProductFavourite = MutableLiveData<DataFavProductResponseItem>()
    val checkProductFavorite: LiveData<DataFavProductResponseItem> = checkItemProductFavourite

    fun checkFav(userId: String, favId: String) {
        liveLoadData.value = true
        Client.checkFav(userId, favId).enqueue(object : Callback<DataFavProductResponseItem> {
            override fun onResponse(
                call: Call<DataFavProductResponseItem>,
                response: Response<DataFavProductResponseItem>
            ) {
                if (response.isSuccessful) {
                    liveLoadData.value = false
                    checkItemProductFavourite.postValue(response.body())
                } else {
                    liveLoadData.value = true
                }
            }

            override fun onFailure(call: Call<DataFavProductResponseItem>, t: Throwable) {
                liveLoadData.value = true
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



}