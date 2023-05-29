package com.lordkajoc.myprojectshop.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DataFav(
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("product_image")
    val productImage: String
): Serializable
