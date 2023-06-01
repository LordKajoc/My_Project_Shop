package com.lordkajoc.myprojectshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lordkajoc.myprojectshop.data.network.ApiService
import com.lordkajoc.myprojectshop.model.DataCart
import com.lordkajoc.myprojectshop.model.DataCartResponseItem
import com.lordkajoc.myprojectshop.model.DataDetailProductItem
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class CartViewModel@Inject constructor(private val Client: ApiService) : ViewModel() {
    private var liveDataCart: MutableLiveData<List<DataCartResponseItem>> = MutableLiveData()
    val dataCart : LiveData<List<DataCartResponseItem>> get() = liveDataCart

    fun postCart(id:String,cart: DataDetailProductItem){
        //memakai callback yang retrofit
        Client.postCart(id,cart).enqueue(object :
            Callback<List<DataCartResponseItem>> {
            override fun onResponse(
                call: Call<List<DataCartResponseItem>>,
                response: Response<List<DataCartResponseItem>>

            ) {
                if (response.isSuccessful){
                    liveDataCart.postValue(response.body())
                }else{
                    liveDataCart.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<List<DataCartResponseItem>>, t: Throwable) {
                liveDataCart.postValue(emptyList())
            }
        })
    }

    fun getCart(userId:String){
        Client.getCart(userId).enqueue(object : Callback<List<DataCartResponseItem>> {
            override fun onResponse(
                call: Call<List<DataCartResponseItem>>,
                response: Response<List<DataCartResponseItem>>
            ) {
                if (response.isSuccessful){
                    liveDataCart.postValue(response.body())
                }else{
                    liveDataCart.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<List<DataCartResponseItem>>, t: Throwable) {
                liveDataCart.postValue(emptyList())
            }

        })
    }

    private val liveDataDeleteCart: MutableLiveData<Boolean> =
        MutableLiveData()
    val dataDeleteCart: LiveData<Boolean> get() = liveDataDeleteCart

    fun deleteCart(idUser: String, idCart: String){
        Client.deleteCart(idUser, idCart).enqueue(object : Callback<Unit> {
            override fun onResponse(
                call: Call<Unit>,
                response: Response<Unit>
            ) {
                if (response.isSuccessful){
                    liveDataDeleteCart.postValue(true)
                }else{
                    liveDataDeleteCart.postValue(false)
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                liveDataDeleteCart.postValue(false)
            }

        })
    }
}