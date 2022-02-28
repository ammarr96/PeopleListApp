package com.amar.peoplelistapp.api

import com.amar.peoplelistapp.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("api/community_{page}.json")
    suspend fun getPeople(@Path("page") page: Int): ApiResponse

}