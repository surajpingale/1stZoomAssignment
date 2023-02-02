package com.example.a1stzoomassignment.model.remote_db

import com.example.a1stzoomassignment.model.remote_db.dataclasses.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("repos/{userName}/{repoName}")
    suspend fun getRepository(
        @Path("userName") userName: String,
        @Path("repoName") repoName: String
    ): Response<ApiResponse>

}