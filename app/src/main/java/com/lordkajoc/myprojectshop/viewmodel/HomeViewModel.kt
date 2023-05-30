package com.lordkajoc.myprojectshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lordkajoc.myprojectshop.data.network.ApiService
import com.lordkajoc.myprojectshop.data.network.RetrofitClient
import com.lordkajoc.myprojectshop.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val Client:ApiService): ViewModel() {
    private var livedataSlider :MutableLiveData<List<DataSlidersResponseItem>> = MutableLiveData()
    val dataSlider: LiveData<List<DataSlidersResponseItem>> get() = livedataSlider

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


    private var livedataNews :MutableLiveData<List<DataNewsResponseItem>> = MutableLiveData()
    val dataNews: LiveData<List<DataNewsResponseItem>> get() = livedataNews
    fun getListNews(){
        //memakai callback yang retrofit
        Client.getNews().enqueue(object : Callback<List<DataNewsResponseItem>> {
            override fun onResponse(
                call: Call<List<DataNewsResponseItem>>,
                response: Response<List<DataNewsResponseItem>>

            ) {
                if (response.isSuccessful){
                    livedataNews.postValue(response.body())
                }else{
                    livedataNews.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<List<DataNewsResponseItem>>, t: Throwable) {
                livedataNews.postValue(emptyList())
            }
        })
    }

    private val liveDetailNews: MutableLiveData<DataDetailNewsItem?> = MutableLiveData()
    val detailNews: LiveData<DataDetailNewsItem?> get() = liveDetailNews

    fun getNewsById(id: Int) {
        Client.getDetailNews(id).enqueue(object : Callback<DataDetailNewsItem> {
            override fun onResponse(
                call: Call<DataDetailNewsItem>,
                response: Response<DataDetailNewsItem>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        liveDetailNews.postValue(responseBody)
                    }
                }
            }
            override fun onFailure(call: Call<DataDetailNewsItem>, t: Throwable) {

            }

        })
    }

    private var liveDataProduct :MutableLiveData<List<DataProductResponseItem>> = MutableLiveData()
    val dataProduct: LiveData<List<DataProductResponseItem>> get() = liveDataProduct
    fun getListProduct(){
        //memakai callback yang retrofit
        Client.getProduct().enqueue(object : Callback<List<DataProductResponseItem>> {
            override fun onResponse(
                call: Call<List<DataProductResponseItem>>,
                response: Response<List<DataProductResponseItem>>

            ) {
                if (response.isSuccessful){
                    liveDataProduct.postValue(response.body())
                }else{
                    liveDataProduct.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<List<DataProductResponseItem>>, t: Throwable) {
                liveDataProduct.postValue(emptyList())
            }
        })
    }

    private val liveDetailProduct: MutableLiveData<DataDetailProductItem?> = MutableLiveData()
    val detailProduct: LiveData<DataDetailProductItem?> get() = liveDetailProduct

    fun getProductById(id: String) {
        Client.getDetailProduct(id).enqueue(object : Callback<DataDetailProductItem> {
            override fun onResponse(
                call: Call<DataDetailProductItem>,
                response: Response<DataDetailProductItem>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        liveDetailProduct.postValue(responseBody)
                    }
                }
            }
            override fun onFailure(call: Call<DataDetailProductItem>, t: Throwable) {

            }

        })
    }
}