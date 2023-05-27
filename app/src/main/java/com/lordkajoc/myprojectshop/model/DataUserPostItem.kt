package com.lordkajoc.myprojectshop.model

import com.google.gson.annotations.SerializedName

data class DataUserPostItem(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("password")
    val password: String? = null
)
