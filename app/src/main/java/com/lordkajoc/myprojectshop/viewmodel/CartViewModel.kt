package com.lordkajoc.myprojectshop.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lordkajoc.myprojectshop.data.network.ApiService
import com.lordkajoc.myprojectshop.model.DataCart
import com.lordkajoc.myprojectshop.model.DataCartResponseItem
import com.lordkajoc.myprojectshop.model.DataDetailProductItem
import com.lordkajoc.myprojectshop.model.DataFavProductResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val Client: ApiService) : ViewModel() {
    private val liveLoadData = MutableLiveData<Boolean>()
    val loadData: LiveData<Boolean> = liveLoadData

    private val postItemCart = MutableLiveData<List<DataCartResponseItem>>()
    val itemCart: LiveData<List<DataCartResponseItem>> = postItemCart
    fun postCart(id: String, name: String, productImage: String, price: Int, desc: String) {
        liveLoadData.value = true
        //memakai callback yang retrofit
        Client.postCart(id, name, productImage, price, desc).enqueue(object :
            Callback<List<DataCartResponseItem>> {
            override fun onResponse(
                call: Call<List<DataCartResponseItem>>,
                response: Response<List<DataCartResponseItem>>

            ) {
                if (response.isSuccessful) {
                    liveLoadData.value = false
                    postItemCart.postValue(response.body())
                } else {
                    liveLoadData.value = false
                }
            }

            override fun onFailure(call: Call<List<DataCartResponseItem>>, t: Throwable) {
                liveLoadData.value = false
            }
        })
    }

    private val getDataListCart = MutableLiveData<List<DataCartResponseItem>>()
    val dataListCart: LiveData<List<DataCartResponseItem>> = getDataListCart
    fun getCart(id: String) {
        liveLoadData.value = true
        Client.getCart(id).enqueue(object : Callback<List<DataCartResponseItem>> {
            override fun onResponse(
                call: Call<List<DataCartResponseItem>>,
                response: Response<List<DataCartResponseItem>>
            ) {
                if (response.isSuccessful) {
                    liveLoadData.value = false
                    getDataListCart.value = response.body()

                } else {
                    liveLoadData.value = false
                }
            }

            override fun onFailure(call: Call<List<DataCartResponseItem>>, t: Throwable) {
                liveLoadData.value = false
            }

        })
    }

    // get item favourite
    private val delItemProductCart = MutableLiveData<DataCartResponseItem>()
    val delProductCart: LiveData<DataCartResponseItem> = delItemProductCart
    fun deleteCartProducts(userId: String, cartId: String) {
        liveLoadData.value = true
        Client.deleteCartProduct(userId, cartId)
            .enqueue(object : Callback<DataCartResponseItem> {
                override fun onResponse(
                    call: Call<DataCartResponseItem>,
                    response: Response<DataCartResponseItem>
                ) {
                    if (response.isSuccessful) {
                        liveLoadData.value = false
                        delItemProductCart.value = response.body()

                    } else {
                        liveLoadData.value = false
                    }
                }

                override fun onFailure(call: Call<DataCartResponseItem>, t: Throwable) {
                    liveLoadData.value = false
                }
            })
    }
}