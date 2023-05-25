package com.lordkajoc.myprojectshop.model


import com.google.gson.annotations.SerializedName

data class DataProductsResponseItem(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String
)