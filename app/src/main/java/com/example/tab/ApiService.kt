package com.example.tab

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("post")
    suspend fun getPost(): Response<List<Post>>

    @GET("post/{id}")
    suspend fun getOnePost(@Path("id") idx: Int): Response<Post>
}