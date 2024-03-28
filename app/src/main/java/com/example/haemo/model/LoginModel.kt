package com.example.haemo.model

import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("id") val studentId: String,
    @SerializedName("password") val password: String
)
