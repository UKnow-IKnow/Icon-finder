package com.example.iconfinder.api

import com.example.iconfinder.model.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface IconApi {
    @GET("icons/search")
    @Headers(
        "Authorization: Bearer S3Bq3b6y3hNhb39BZLtlndI2q5n8mW6TV8LLPUQWm2sFvyQlAdrl894r03Nr5e98"
    )
    suspend fun getIcons(@QueryMap params: Map<String, String>): Response<ApiResponse>


    @GET
    @Streaming
    @Headers(
        "Authorization: Bearer S3Bq3b6y3hNhb39BZLtlndI2q5n8mW6TV8LLPUQWm2sFvyQlAdrl894r03Nr5e98"
    )
    fun downloadFile(@Url url: String): Call<ResponseBody>
}