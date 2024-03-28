package com.example.haemo.model

import com.google.gson.annotations.SerializedName

data class PostModel(
    @SerializedName("title") val title: String,
    @SerializedName("nickname") val nickname: String
)
