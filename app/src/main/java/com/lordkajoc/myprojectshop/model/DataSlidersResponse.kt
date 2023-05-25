package com.lordkajoc.myprojectshop.model


import com.google.gson.annotations.SerializedName

data class DataSlidersResponse(
    @SerializedName("results")
    val results: List<DataSlidersResponseItem>
)