package com.lordkajoc.myprojectshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lordkajoc.myprojectshop.data.network.ApiService
import com.lordkajoc.myprojectshop.data.network.RetrofitClient
import com.lordkajoc.myprojectshop.model.DataSlidersResponse
import com.lordkajoc.myprojectshop.model.DataSlidersResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val sliderClient:ApiService): ViewModel() {
    private var livedataSlider :MutableLiveData<List<DataSlidersResponseItem>> = MutableLiveData()
    val dataSlider: LiveData<List<DataSlidersResponseItem>> get() = livedataSlider

//    fun setSliderList() {
//        sliderClient.getSliders()
//            .enqueue(object : Callback<DataSlidersResponse> {
//                override fun onResponse(
//                    call: Call<DataSlidersResponse>,
//                    response: Response<DataSlidersResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val data = response.body()
//                        if (data != null) {
//                            livedataSlider.postValue(data.results as List<DataSlidersResponseItem>?)
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<DataSlidersResponse>, t: Throwable) {
//
//                }
//
//            })
//    }

    fun getSlider(){
        //memakai callback yang retrofit
        RetrofitClient.instance.getSliders().enqueue(object : Callback<List<DataSlidersResponseItem>> {
            override fun onResponse(
                call: Call<List<DataSlidersResponseItem>>,
                response: Response<List<DataSlidersResponseItem>>

            ) {
                if (response.isSuccessful){
                    livedataSlider.postValue(response.body())
//                    val newsresponse = response.body()
//                    liveDataNews.postValue(newsresponse!!)
                }else{
                    livedataSlider.postValue(emptyList())
//                    liveDataNews.value = emptyList()
                }
            }

            override fun onFailure(call: Call<List<DataSlidersResponseItem>>, t: Throwable) {
                livedataSlider.postValue(emptyList())
//                liveDataNews.value = emptyList()
            }

        })
    }
}