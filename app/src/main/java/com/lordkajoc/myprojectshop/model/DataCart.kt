package com.lordkajoc.myprojectshop.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DataCart (
    @SerializedName("id_cart")
    val idCart: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("product_image")
    val productImage: String
    ): Serializable