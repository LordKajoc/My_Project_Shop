package com.lordkajoc.myprojectshop.model

import com.google.gson.annotations.SerializedName

data class DataDetailProductItem(
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("name")
    val name: String? = null
)