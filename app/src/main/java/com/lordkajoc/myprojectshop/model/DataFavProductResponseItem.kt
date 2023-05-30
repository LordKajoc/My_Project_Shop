package com.lordkajoc.myprojectshop.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DataFavProductResponseItem(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id_fav")
    val idFav: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("product_image")
    val productImage: String,
    @SerializedName("userId")
    val userId: String
): Serializable