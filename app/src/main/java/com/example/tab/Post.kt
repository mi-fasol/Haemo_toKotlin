package com.example.tab

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("title") val title: String,
    @SerializedName("nickname") val nickname: String
)
