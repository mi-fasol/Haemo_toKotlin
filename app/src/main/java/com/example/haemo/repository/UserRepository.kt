package com.example.haemo.repository

import com.example.haemo.model.LoginModel
import com.example.haemo.model.PostModel
import com.example.haemo.network.RetrofitClient
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val retrofitClient: RetrofitClient) {

    suspend fun tryLogin(loginModel: LoginModel): Response<Boolean> {
        return retrofitClient.service.tryLogin(loginModel)
    }

}