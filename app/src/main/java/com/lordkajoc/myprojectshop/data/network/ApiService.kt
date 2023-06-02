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

    @PUT("users/{id}")
    fun putUpdateUser(
        @Path("id") userId: String,
        @Body request: DataUsersResponseItem
    ) : Call<DataUsersResponseItem>

    @FormUrlEncoded
    @POST("users/{id}/favourite")
    fun postFavouriteProduct(
        @Path("id") id:String,
        @Field("name") name:String,
        @Field("product_image") productImage:String,
        @Field("price") price:Int,
        @Field("description") description:String,
    ):Call<DataFavProductResponseItem>

    @DELETE("users/{userId}/favourite/{id}")
    fun deleteFavouriteProduct(
        @Path("userId") userId:String,
        @Path("id") id:String
    ):Call<DataFavProductResponseItem>

    @GET("users/{id}/favourite")
    fun getFavorite(@Path("id") userId: String,
    ) : Call<List<DataFavProductResponseItem>>

    @GET("users/{id}/favourite/{idFav}")
    fun checkFav(
        @Path("id") userId:String,
        @Path("idFav") id:String
    ) : Call<DataFavProductResponseItem>

    @FormUrlEncoded
    @POST("users/{id}/cart")
    fun postCart(
        @Path("id") id:String,
        @Field("name") name:String,
        @Field("product_image") productImage:String,
        @Field("price") price:Int,
        @Field("description") description:String)
    : Call <List<DataCartResponseItem>>

    @GET("users/{id}/cart")
    fun getCart(
        @Path("id") id: String
    ): Call<List<DataCartResponseItem>>

    @DELETE("users/{userId}/cart/{id}")
    fun deleteCartProduct(
        @Path("userId") userId:String,
        @Path("id") id:String
    ):Call<DataCartResponseItem>
}