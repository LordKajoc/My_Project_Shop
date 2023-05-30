package com.lordkajoc.myprojectshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.play.integrity.internal.t
import com.lordkajoc.myprojectshop.data.network.ApiService
import com.lordkajoc.myprojectshop.model.DataCart
import com.lordkajoc.myprojectshop.model.DataDetailProductItem
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class CartViewModel@Inject constructor(private val Client: ApiService) : ViewModel() {
    private var liveDataCart: MutableLiveData<List<DataCart>> = MutableLiveData()
    val dataCart : LiveData<List<DataCart>> get() = liveDataCart

    fun postCart(id:String,cart: DataDetailProductItem){
        //memakai callback yang retrofit
        Client.postCart(id,cart).enqueue(object :
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

    fun getCart(userId: String){
        Client.getCart(userId).enqueue(object : Callback<List<DataCart>> {
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