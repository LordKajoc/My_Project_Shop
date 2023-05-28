package com.lordkajoc.myprojectshop.model

import com.google.gson.annotations.SerializedName

data class DataUsers(
    @SerializedName("email")
    val email: String,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String

)
