package com.lordkajoc.myprojectshop.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DataUsersResponseItem(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id_users")
    val idUsers: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String
): Serializable