package com.example.contentProviderApp.network

import com.example.contentProviderApp.model.ApiResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

class ApiService {
    interface Main {
        @GET("{id}")
        fun getUserDetails(@Path("id") userId: String): Single<ApiResponse>
    }
}