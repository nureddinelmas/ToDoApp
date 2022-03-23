package com.nureddinelmas.todoapp.repository

import com.nureddinelmas.todoapp.api.RetrofitInstance
import com.nureddinelmas.todoapp.model.Post
import retrofit2.Response

class Repository {
    suspend fun pushPost(post: Post): Response<Post>{
        return RetrofitInstance.api.pushPost(post)
    }
}