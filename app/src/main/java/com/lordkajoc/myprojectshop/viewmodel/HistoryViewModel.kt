package com.lordkajoc.myprojectshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lordkajoc.myprojectshop.data.network.ApiService
import com.lordkajoc.myprojectshop.model.DataCartResponseItem
import com.lordkajoc.myprojectshop.model.DataTransHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val Client: ApiService) : ViewModel() {
    private val liveLoadData = MutableLiveData<Boolean>()
    val loadData: LiveData<Boolean> = liveLoadData
    private val postItemTrans = MutableLiveData<List<DataTransHistory>>()

    fun postTrans(id: String, name: String, productImage: String, price: Int, desc: String) {
        liveLoadData.value = true
        //memakai callback yang retrofit
        Client.postHistory(id, name, productImage, price, desc).enqueue(object :
            Callback<List<DataTransHistory>> {
            override fun onResponse(
                call: Call<List<DataTransHistory>>,
                response: Response<List<DataTransHistory>>

            ) {
                if (response.isSuccessful) {
                    liveLoadData.value = false
                    postItemTrans.postValue(response.body())
                } else {
                    liveLoadData.value = false
                }
            }

            override fun onFailure(call: Call<List<DataTransHistory>>, t: Throwable) {
                liveLoadData.value = false
            }
        })

    }
    private val getDataListTransHistory = MutableLiveData<List<DataTransHistory>>()
    val dataListTransHistory: LiveData<List<DataTransHistory>> = getDataListTransHistory
    fun getCart(id: String) {
        liveLoadData.value = true
        Client.getTransHistory(id).enqueue(object : Callback<List<DataTransHistory>> {
            override fun onResponse(
                call: Call<List<DataTransHistory>>,
                response: Response<List<DataTransHistory>>
            ) {
                if (response.isSuccessful) {
                    liveLoadData.value = false
                    getDataListTransHistory.value = response.body()

                } else {
                    liveLoadData.value = false
                }
            }

            override fun onFailure(call: Call<List<DataTransHistory>>, t: Throwable) {
                liveLoadData.value = false
            }

        })
    }
}