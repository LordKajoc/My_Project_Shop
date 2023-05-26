package com.lordkajoc.myprojectshop.data.network

import com.lordkajoc.myprojectshop.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    //Get Sliders
    @GET("sliders")
    fun getSliders(): Call<List<DataSlidersResponseItem>>

    //Get News and Detail on Click by Id
    @GET("news_update")
    fun getNews(): Call<List<DataNewsResponseItem>>
    @GET("news_update/{id}?")
    fun getDetailNews(@Path("id") id:Int): Call<DataDetailNewsItem>

    //Get product and Detail on Click by Id
    @GET("category_product")
    fun getProduct(): Call<List<DataProductsResponseItem>>
    @GET("category_product/{id}?")
    fun getDetailProduct(@Path("id") id:Int): Call<DataDetailProductItem>
}