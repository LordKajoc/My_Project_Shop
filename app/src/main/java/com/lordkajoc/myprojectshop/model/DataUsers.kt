package com.lordkajoc.myprojectshop.model

import com.google.gson.annotations.SerializedName

data class DataUsers(
    val email: String?,
    val image: String? = "",
    val name: String?,
    val password: String?
)
