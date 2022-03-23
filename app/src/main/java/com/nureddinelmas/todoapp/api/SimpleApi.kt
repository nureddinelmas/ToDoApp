package com.nureddinelmas.todoapp.api

import com.nureddinelmas.todoapp.model.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SimpleApi {
    @POST("login")
    suspend fun pushPost(
        @Body post: Post
    ): Response<Post>
}