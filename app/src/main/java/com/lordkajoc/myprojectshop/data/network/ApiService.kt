package com.lordkajoc.myprojectshop.data.network

import com.lordkajoc.myprojectshop.model.*
import retrofit2.Call
import retrofit2.http.*

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
    @GET("category_product/4/products")
    fun getProduct(): Call<List<DataProductResponseItem>>
    @GET("category_product/4/products/{id}?")
    fun getDetailProduct(@Path("id") id:Int): Call<DataDetailProductItem>

    //Get User and All Data User for profile
    @GET("users")
    fun getAllUser(): Call<List<DataUsersResponseItem>>

    @POST("users")
    fun registerUser(@Body request: DataUsers): Call<DataUserPostItem>

//    @PUT("users/{id}")
//    fun updateUser(@Path("id") id : Int, @Body request: DataProfile): Call<PostUserResponse>

}