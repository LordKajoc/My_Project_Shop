package com.lordkajoc.myprojectshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lordkajoc.myprojectshop.data.network.ApiService
import com.lordkajoc.myprojectshop.data.network.RetrofitClient
import com.lordkajoc.myprojectshop.model.DataDetailNewsItem
import com.lordkajoc.myprojectshop.model.DataNewsResponseItem
import com.lordkajoc.myprojectshop.model.DataSlidersResponse
import com.lordkajoc.myprojectshop.model.DataSlidersResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val Client:ApiService): ViewModel() {
    private var livedataSlider :MutableLiveData<List<DataSlidersResponseItem>> = MutableLiveData()
    val dataSlider: LiveData<List<DataSlidersResponseItem>> get() = livedataSlider

    private var livedataNews :MutableLiveData<List<DataNewsResponseItem>> = MutableLiveData()
    val dataNews: LiveData<List<DataNewsResponseItem>> get() = livedataNews

//    private var livedatadetailNews :MutableLiveData<List<DataDetailNewsItem>?> = MutableLiveData()
//    val datadetailNews: LiveData<List<DataDetailNewsItem>?> get() = livedatadetailNews

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
        Client.getSliders().enqueue(object : Callback<List<DataSlidersResponseItem>> {
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

    fun getListNews(){
        //memakai callback yang retrofit
        Client.getNews().enqueue(object : Callback<List<DataNewsResponseItem>> {
            override fun onResponse(
                call: Call<List<DataNewsResponseItem>>,
                response: Response<List<DataNewsResponseItem>>

            ) {
                if (response.isSuccessful){
                    livedataNews.postValue(response.body())
//                    val newsresponse = response.body()
//                    liveDataNews.postValue(newsresponse!!)
                }else{
                    livedataNews.postValue(emptyList())
//                    liveDataNews.value = emptyList()
                }
            }

            override fun onFailure(call: Call<List<DataNewsResponseItem>>, t: Throwable) {
                livedataNews.postValue(emptyList())
//                liveDataNews.value = emptyList()
            }
        })
    }

//    fun getDetailNewsbyId(id:Int){
//        //memakai callback yang retrofit
//        Client.getDetailNews(id).enqueue(object : Callback<List<DataDetailNewsItem>> {
//            override fun onResponse(
//                call: Call<List<DataDetailNewsItem>>,
//                response: Response<List<DataDetailNewsItem>>
//
//            ) {
//                if (response.isSuccessful){
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        livedatadetailNews.postValue(responseBody)
//                    }
//                }else{
//                    livedataNews.postValue(emptyList())
////                    liveDataNews.value = emptyList()
//                }
//            }
//
//            override fun onFailure(call: Call<List<DataDetailNewsItem>>, t: Throwable) {
//                livedataNews.postValue(emptyList())
////                liveDataNews.value = emptyList()
//            }
//        })
//    }
}