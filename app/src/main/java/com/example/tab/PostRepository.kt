package com.example.tab

import retrofit2.Response

class PostRepository {

    suspend fun getPost(): Response<List<Post>> {
        return RetrofitClient.service.getPost()
    }

    suspend fun getOnePost(idx: Int): Response<Post> {
        return RetrofitClient.service.getOnePost(idx)
    }

}