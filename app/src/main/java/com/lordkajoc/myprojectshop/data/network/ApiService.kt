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
    @GET("news_update/{id}")
    fun getDetailNews(@Path("id") id:Int): Call<DataDetailNewsItem>

    //Get product and Detail on Click by Id
    @GET("category_product/4/products")
    fun getProduct(): Call<List<DataProductResponseItem>>
    @GET("category_product/4/products/{id}")
    fun getDetailProduct(@Path("id") id:String): Call<DataDetailProductItem>

    //Get User and All Data User for profile
    @GET("users")
    fun getAllUser(): Call<List<DataUsersResponseItem>>

    @POST("users")
    fun registerUser(@Body request: DataUsersResponseItem): Call<List<DataUserPostItem>>

    @GET("users/{id}?")
    fun getProfileUser(@Path("id") id:String): Call<DataUsersResponseItem>

//    @PUT("users/{id}")
//    fun updateUser(@Path("id") id : Int, @Body request: DataProfile): Call<PostUserResponse>

    @POST("user/{id}?/favourite")
    fun getPostFavorite(
        @Path("id") userId: String,
        @Body request : DataFavProductResponseItem
    ) : Call<List<DataFavProductResponseItem>>

    @DELETE("user/{id}/favourite/{idProduct}")
    fun getDeleteFavorite(
        @Path("id") userId : String,
        @Path("idProduct") idProduct : String
    ) : Call<String>

    @GET("users/99/favourite/{id}")
    fun check(@Path("id") id: String): Call<Boolean>

    @POST("users/{id}/cart")
    fun postCart(@Path("id") id:String,
                 @Body request: DataDetailProductItem) : Call <List<DataCart>>

    @GET("users/{id}/cart")
    fun getCart(@Path("id")id:String): Call<List<DataCart>>
}