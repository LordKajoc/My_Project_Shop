package com.lordkajoc.myprojectshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lordkajoc.myprojectshop.data.network.ApiService
import com.lordkajoc.myprojectshop.model.DataCart
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class CartViewModel@Inject constructor(private val Client: ApiService) : ViewModel() {
    private var liveDataCart: MutableLiveData<List<DataCart>> = MutableLiveData()
    val dataCart : LiveData<List<DataCart>> get() = liveDataCart

    fun postCart(cart: DataCart){
        //memakai callback yang retrofit
        Client.postCart(cart).enqueue(object :
            Callback<List<DataCart>> {
            override fun onResponse(
                call: Call<List<DataCart>>,
                response: Response<List<DataCart>>

            ) {
                if (response.isSuccessful){
                    liveDataCart.postValue(response.body())
                }else{
                    liveDataCart.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<List<DataCart>>, t: Throwable) {
                liveDataCart.postValue(emptyList())
            }
        })
    }
}