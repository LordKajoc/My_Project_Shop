package com.lordkajoc.myprojectshop.data.network

import com.lordkajoc.myprojectshop.model.DataNewsResponseItem
import com.lordkajoc.myprojectshop.model.DataSlidersResponse
import com.lordkajoc.myprojectshop.model.DataSlidersResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("sliders")
    fun getSliders(): Call<List<DataSlidersResponseItem>>

    @GET("news_update")
    fun getNews(): Call<DataNewsResponseItem>

    @GET("news_update/{id}")
    fun getDetailNews(@Path("id") id:Int): Call<DataNewsResponseItem>
}