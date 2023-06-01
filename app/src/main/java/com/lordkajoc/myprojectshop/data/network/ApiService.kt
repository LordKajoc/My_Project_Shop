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

    @POST("users/{id}/favourite")
    fun getPostFavorite(
        @Path("id") userId: String,
        @Body request : DataDetailProductItem
    ) : Call<List<DataFavProductResponseItem>>

    @GET("users/{id}/favourite")
    fun getFavorite(@Path("id") userId: String,
    ) : Call<List<DataFavProductResponseItem>>

    @DELETE("users/99/favourite/{idFav}")
    fun getDeleteFavorite(
//        @Path("id") userId : String,
        @Path("idFav") idFav : String
    ) : Call<Unit>

    @GET("users/99/favourite/{idFav}")
    fun checkFav(
//        @Path("id") userId : String,
        @Path("idFav") idFav : String
    ) : Call<Boolean>

    @POST("users/{id}/cart")
    fun postCart(
        @Path("id") id: String,
        @Body request: DataDetailProductItem) : Call <List<DataCartResponseItem>>

    @GET("users/{id}/cart")
    fun getCart(
        @Path("id") id: String
    ): Call<List<DataCartResponseItem>>

    @DELETE("users/{id}/cart/{idCart}")
    fun deleteCart(
        @Path("id") id:String,
        @Path("idCart") idCart : String
    ):Call<Unit>
}