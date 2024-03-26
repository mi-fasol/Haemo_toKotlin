package com.example.tab

import retrofit2.Response
import javax.inject.Inject

class PostRepository @Inject constructor(private val retrofitClient: RetrofitClient) {

    suspend fun getPost(): Response<List<Post>> {
        return retrofitClient.service.getPost()
    }

    suspend fun getOnePost(idx: Int): Response<Post> {
        return retrofitClient.service.getOnePost(idx)
    }

}